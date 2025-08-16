package dev.ddemianyk.geminiai.web.controller;

import dev.ddemianyk.geminiai.web.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    @GetMapping("/api/userinfo")
    public UserInfo profile(@AuthenticationPrincipal OAuth2User principal) {
        return new UserInfo(
                principal.getAttribute("email"),
                principal.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
        );
    }
}
