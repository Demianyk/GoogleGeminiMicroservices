package dev.ddemianyk.geminiai.web.utils;

import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserInfoUtils {

    public static long getUserId(Principal principal) {
        return emailToLong(getUserEmail(principal));
    }

    private static long emailToLong(String email) {
        try {
            // Create a SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the email string
            byte[] hash = digest.digest(email.getBytes());

            // Convert the first 8 bytes of the hash to a long
            ByteBuffer buffer = ByteBuffer.wrap(hash);
            return buffer.getLong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static String getUserEmail(Principal principal) {
        String email = "";
        if (principal instanceof OAuth2AuthenticationToken token) {
            email = (String) token.getPrincipal().getAttributes().get("email");
        }
        return email;
    }
}
