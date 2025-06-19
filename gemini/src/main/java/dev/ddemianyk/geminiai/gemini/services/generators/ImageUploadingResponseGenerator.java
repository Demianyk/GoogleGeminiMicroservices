package dev.ddemianyk.geminiai.gemini.services.generators;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.File;
import com.google.genai.types.FileData;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Part;
import com.google.genai.types.UploadFileConfig;
import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.config.ModelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageUploadingResponseGenerator implements ResponseGenerator {

    private final ModelProperties modelProperties;

    @Override
    public String generate(UserMessage userMessage) {
        return generateWithImage(
                userMessage.text(),
                userMessage.picture());
    }

    private String generateWithImage(String userMessage, byte[] file) {
        try (Client client = new Client()) {
            // Upload the image file
            File f = client.files.upload(file, UploadFileConfig.builder()
                    .mimeType("image/jpeg")
                    .build());

            // Send the image and user message to the Gemini modelName and return a response
            return client.models.generateContent(
                    modelProperties.modelName(),
                    Content.builder()
                            .parts(List.of(
                                    Part.builder()
                                            .fileData(FileData.builder()
                                                    .fileUri(f.uri().get())
                                                    .build())
                                            .build(),
                                    Part.builder()
                                            .text(userMessage)
                                            .build()
                            )).build(),
                    GenerateContentConfig.builder()
                            .build()).text();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
