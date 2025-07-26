package dev.ddemianyk.geminiai.web.config;

import dev.ddemianyk.geminiai.web.utils.UserInfoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Value("${front-end-url:}")
    private String spaUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .anyRequest().access((authentication, context) -> new AuthorizationDecision(allowedEmails().contains(UserInfoUtils.getUserEmail(authentication.get()))))
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .oauth2Login(
                        oauth2 -> oauth2
                                .defaultSuccessUrl(spaUrl + "/spa", true)
                                .authorizationEndpoint(
                                        authorizationEndpointConfig -> authorizationEndpointConfig
                                                .authorizationRequestResolver(
                                                        new SelectAccountAuthorizationRequestResolver(
                                                                clientRegistrationRepository,
                                                                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
                                                        )
                                                )
                                )
                );
        return http.build();
    }

    private static List<String> allowedEmails() {
        String concatenated = System.getenv("AUTHORIZED_EMAILS");
        return concatenated != null ? List.of(concatenated.split(";")) : List.of();
    }
}
