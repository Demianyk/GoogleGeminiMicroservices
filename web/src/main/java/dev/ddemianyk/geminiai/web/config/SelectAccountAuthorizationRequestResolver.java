package dev.ddemianyk.geminiai.web.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * This class customizes the OAuth2 authorization request to include the "prompt=select_account" parameter.
 * This is useful for scenarios where you want to force the user to select an account during the OAuth2 login flow.
 */
public class SelectAccountAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final OAuth2AuthorizationRequestResolver defaultResolver;

    public SelectAccountAuthorizationRequestResolver(ClientRegistrationRepository repo, String baseUri) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, baseUri);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest original = defaultResolver.resolve(request);
        return customize(original);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest original = defaultResolver.resolve(request, clientRegistrationId);
        return customize(original);
    }

    private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest original) {
        if (original == null) return null;

        Map<String, Object> extraParams = new HashMap<>(original.getAdditionalParameters());
        extraParams.put("prompt", "select_account");

        return OAuth2AuthorizationRequest.from(original)
                .additionalParameters(extraParams)
                .build();
    }
}
