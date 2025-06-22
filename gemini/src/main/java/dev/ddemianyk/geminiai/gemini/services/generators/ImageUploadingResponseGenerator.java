package dev.ddemianyk.geminiai.gemini.services.generators;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.FileData;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Part;
import com.google.genai.types.UploadFileConfig;
import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.config.ModelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageUploadingResponseGenerator implements ResponseGenerator {

    private final ModelProperties modelProperties;
    private final List<Content> chatHistory = new ArrayList<>();

    @Override
    public String generate(UserMessage userMessage) {
        try (Client client = new Client()) {
            // Send the user message to the Gemini modelName and return a response
            var response = client.models.generateContent(
                    modelProperties.modelName(),
                    content(userMessage),
                    GenerateContentConfig.builder()
                            .build());
            return response.text();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    private static boolean imageIsPresent(UserMessage userMessage) {
        return userMessage.picture() != null && userMessage.picture().length > 0;
    }

    private static Content content(UserMessage userMessage) {
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
        return Content.builder()
                .parts(parts)
                .role("user")
                .build();
    }
}
