package dev.ddemianyk.geminiai.gemini.services.generators;

import com.google.genai.Client;
import com.google.genai.types.Candidate;
import com.google.genai.types.Content;
import com.google.genai.types.FileData;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Part;
import com.google.genai.types.UploadFileConfig;
import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.config.ModelProperties;
import dev.ddemianyk.geminiai.gemini.services.content.ContentManager;
import lombok.RequiredArgsConstructor;
import static org.apache.http.util.TextUtils.isEmpty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ImageUploadingResponseGenerator implements ResponseGenerator {

    private final ModelProperties modelProperties;
    private final ContentManager contentManager;
    private final List<Content> chatHistory = new ArrayList<>();

    @Override
    public String generate(UserMessage userMessage) {
        try (Client client = new Client()) {
            // Send the user message to the Gemini modelName and return a response
            addUserContent(userMessage);
            var contentOptional = client.models.generateContent(
                            modelProperties.modelName(),
                            contentManager.getContent(userMessage.userId()),
                            GenerateContentConfig.builder()
                                    .build())
                    .candidates().stream()
                    .flatMap(Collection::stream)
                    .filter(candidate -> candidate.content().isPresent())
                    .map(Candidate::content)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(content -> !isEmpty(content.text()))
                    .findFirst();

            contentOptional.ifPresent(responseContent -> contentManager.addContent(userMessage.userId(), responseContent));
            return contentOptional
                    .map(Content::text)
                    .orElse("No response generated");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    private static boolean imageIsPresent(UserMessage userMessage) {
        return userMessage.picture() != null && userMessage.picture().length > 0;
    }

    private void addUserContent(UserMessage userMessage) {
        var textPart = Part.builder().text(userMessage.text()).build();
        var parts = new ArrayList<Part>();
        parts.add(textPart);
        if (imageIsPresent(userMessage)) {
            try (Client client = new Client()) {
                client.files
                        .upload(
                                userMessage.picture(),
                                UploadFileConfig.builder()
                                        .mimeType("image/jpeg")
                                        .build())
                        .uri()
                        .map(imageUri -> Part.builder()
                                .fileData(FileData.builder().fileUri(imageUri).build())
                                .build()
                        )
                        .ifPresent(parts::add);
            }
        }
        contentManager.addContent(userMessage.userId(), Content.builder()
                .parts(parts)
                .role("user")
                .build());
    }
}
