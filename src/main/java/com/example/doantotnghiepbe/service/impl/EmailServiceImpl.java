package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.Payment;
import com.example.doantotnghiepbe.entity.Post;
import com.example.doantotnghiepbe.entity.Price;
import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.repository.PaymentRepository;
import com.example.doantotnghiepbe.repository.PostRepository;
import com.example.doantotnghiepbe.repository.PriceRepository;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.EmailService;
import com.example.doantotnghiepbe.util.JwtTokenUtil;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public void sendsendSimpleEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("baonvhps34367@fpt.edu.vn");

        javaMailSender.send(message);
        System.out.println("Gửi email thành công!");
    }

    @Override
    public String sendVerificationEmail(Long userId, String verificationUrl) throws Exception {
        // Tạo verification token
        Users user = usersRepository.findById(userId).orElseThrow(()-> new DataNotFoundException("ko tìm thấy id:" + userId));
        String token = jwtTokenUtil.generateVerificationToken(user);
        user.setTokenVerified(token);
        usersRepository.save(user);
        String tokenVerified = user.getTokenVerified();
        System.out.println(tokenVerified);
        String apiUrl = "http://127.0.0.1:5500/app/components/user/VerificatonResult.html?token="+tokenVerified;

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(user.getEmail());
        helper.setSubject("Account Verification");

        // Tạo nội dung HTML với button xác thực
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>Welcome to Our Service!</h2>"
                + "<p>Thank you for registering. Please click the button below to verify your account:</p>"
                + "<a href=\"" + apiUrl + "\" style=\""
                + "background-color: #4CAF50;"
                + "color: white;"
                + "padding: 10px 20px;"
                + "text-align: center;"
                + "text-decoration: none;"
                + "display: inline-block;"
                + "border-radius: 5px;\">Verify Account</a>"
                + "<p>If you didn't register, please ignore this email.</p>"
                + "</body>"
                + "</html>";

        helper.setText(htmlContent, true);
        helper.setFrom(user.getEmail(),"PFR");

        javaMailSender.send(mimeMessage);
        return token;
    }

    @Override
    public void sendPaymentResultEmail(String txnRef) throws Exception {

        // Tìm thông tin thanh toán qua txnRef
        Payment payment = paymentRepository.findByVnpTxnRef(txnRef);
        if (payment == null) {
            throw new DataNotFoundException("Không tìm thấy mã thanh toán: " + txnRef);
        }

        Price price = priceRepository.findByPriceId(payment.getPriceId().getPriceId());
        Post post = payment.getPostId();

        if (price == null) {
            throw new DataNotFoundException("Không tìm thấy giá với ID: " + payment.getPriceId());
        }

        if (post == null) {
            throw new DataNotFoundException("Không tìm thấy bài đăng với ID: " + payment.getPostId());
        }

        // Lấy thông tin người dùng liên quan đến bài đăng
        Users user = usersRepository.findByUserId(post.getUser().getUserId());
        if (user == null) {
            throw new DataNotFoundException("Không tìm thấy người dùng với ID: " + post.getUser());
        }

        // Định dạng số tiền
        DecimalFormat df = new DecimalFormat("#,### VND");
        String formattedAmount = df.format(payment.getPaymentAmount());

        // Định dạng thời gian với timezone
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh"); // You can set your own timezone here
        ZonedDateTime paymentDateZoned = payment.getPaymentDate().atZone(zoneId);
        String formattedPaymentDate = paymentDateZoned.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z"));

        ZonedDateTime topPostEndZoned = post.getTopPostEnd() != null ? post.getTopPostEnd().atZone(zoneId) : null;
        String formattedTopPostEnd = topPostEndZoned != null ? topPostEndZoned.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z")) : "Chưa có thời gian";

        // Tạo nội dung email
        String subject = "Kết quả thanh toán đơn hàng";
        String status = payment.getPaymentStatus().equals("Success") ? "Thành công" : "Thất bại";
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>Xin chào " + user.getLastName() + " " + user.getFirstName() + ",</h2>"
                + "<p>Dưới đây là thông tin chi tiết giao dịch của bạn:</p>"
                + "<ul>"
                + "<li><strong>Mã thanh toán:</strong> " + txnRef + "</li>"
                + "<li><strong>Trạng thái thanh toán:</strong> " + status + "</li>"
                + "<li><strong>Số tiền:</strong> " + formattedAmount + "</li>"
                + "<li><strong>Thời gian giao dịch:</strong> " + formattedPaymentDate + "</li>"
                + (status.equals("Thành công")
                ? "<li><strong>Số ngày đẩy top:</strong> " + price.getDuration() + " ngày</li>"
                + "<li><strong>Thời hạn đẩy top đến ngày:</strong> " + formattedTopPostEnd + "</li>"
                : "")
                + "</ul>"
                + "<i>Lưu ý: 'Thời gian đẩy top đến ngày' được tính cộng dồn với những lần thanh toán trước đó của bạn (nếu có).</i>"
                + "<p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.</p>"
                + "</body>"
                + "</html>";

        // Tạo email và thiết lập thông tin gửi
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom("baonvhps34367@fpt.edu.vn", "FPR");

        // Gửi email
        javaMailSender.send(mimeMessage);

        System.out.println("Email kết quả thanh toán đã được gửi tới: " + user.getEmail());
    }


}

