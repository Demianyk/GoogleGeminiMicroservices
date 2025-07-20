package dev.ddemianyk.geminiai.web.controller;

import dev.ddemianyk.geminiai.web.model.UserInfo;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {
    @GetMapping("/api/userinfo")
    public UserInfo profile(Principal principal) {
        String email = "";
        if (principal instanceof OAuth2AuthenticationToken token) {
            email = (String) token.getPrincipal().getAttributes().get("email");
        }
        return new UserInfo(email);
    }
}
