package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.Users;
import com.example.doantotnghiepbe.exceptions.DataNotFoundException;
import com.example.doantotnghiepbe.repository.UsersRepository;
import com.example.doantotnghiepbe.service.EmailService;
import com.example.doantotnghiepbe.util.JwtTokenUtil;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsersRepository usersRepository;

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
        String url = verificationUrl+token;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(user.getEmail());
        helper.setSubject("Account Verification");

        // Tạo nội dung HTML với button xác thực
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>Welcome to Our Service!</h2>"
                + "<p>Thank you for registering. Please click the button below to verify your account:</p>"
                + "<a href=\"" + verificationUrl + "\" style=\""
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

        helper.setText(htmlContent, true); // Set true to indicate HTML content
        helper.setFrom("baonvhps34367@fpt.edu.vn");

        javaMailSender.send(mimeMessage);
        return token;
    }

}

