package dev.ddemianyk.geminiai.web.config;

import dev.ddemianyk.geminiai.web.model.UserInfo;
import dev.ddemianyk.geminiai.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(userRequest);
        String email = oauthUser.getAttribute("email");
        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from Google account");
        }
        UserInfo user = userService.findByEmail(email);
        if (user == null) {
            throw new OAuth2AuthenticationException("No local account found for email: " + email);
        }
        List<SimpleGrantedAuthority> authorities = user.authorities().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new DefaultOAuth2User(
                authorities,
                oauthUser.getAttributes(), // keep Google's attributes like "name", "picture"
                "email" // key for the username
        );
    }
}
