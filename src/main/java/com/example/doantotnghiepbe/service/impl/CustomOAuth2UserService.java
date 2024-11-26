//package com.example.doantotnghiepbe.service.impl;
//
//import com.example.doantotnghiepbe.entity.Users;
//import com.example.doantotnghiepbe.repository.UsersRepository;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
////import org.springframework.security.oauth2.client.oidc.userinfo.DefaultOidcUser;
//
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class CustomOAuth2UserService extends OidcUserService {
//
//    private final UsersRepository userRepository;
//
//    public CustomOAuth2UserService(UsersRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public OidcUser loadUser(OidcUserRequest userRequest) {
//        OidcUser oidcUser = super.loadUser(userRequest);
//
//        Map<String, Object> attributes = oidcUser.getAttributes();
//        String email = (String) attributes.get("email");
//        String name = (String) attributes.get("name");
//        String picture = (String) attributes.get("picture");
//
//        Users user = userRepository.findByEmail(email);
//        if (user == null) {
//            user = new Users();
//            user.setEmail(email);
//            user.setLastName(name);
//            user.setAvatar(picture);
//            userRepository.save(user);
//        }
//
//        return new DefaultOidcUser(
//                oidcUser.getAuthorities(),
//                oidcUser.getIdToken(),
//                oidcUser.getUserInfo());
//    }
//}
