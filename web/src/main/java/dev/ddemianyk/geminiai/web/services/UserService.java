package dev.ddemianyk.geminiai.web.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import dev.ddemianyk.geminiai.web.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final Firestore db;

    public UserInfo findByEmail(String email) {
        try {
            return db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .get()
                    .getDocuments()
                    .stream()
                    .findFirst()
                    .map(doc -> new UserInfo(
                            doc.getString("email"),
                            getRoles(doc)
                    ))
                    .orElse(null);
        } catch (Exception e) {
            log.error("Error checking if user exists: {}", e.getMessage(), e);
            return new UserInfo(email, java.util.Set.of());
        }
    }

    private static Set<String> getRoles(DocumentSnapshot documentSnapshot) {
        String rolesString = documentSnapshot.get("authorities", String.class);
        if (rolesString == null || rolesString.isEmpty()) {
            return Set.of();
        }
        return Set.of(rolesString.split(";"));
    }

}
