package dev.ddemianyk.geminiai.telegram.service.ai.generators;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;

import dev.ddemianyk.geminiai.telegram.config.ModelProperties;
import dev.ddemianyk.geminiai.telegram.model.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TextMessageResponseGenerator implements ResponseGenerator {

    private final ModelProperties modelProperties;

    @Override
    public String generate(UserMessage userMessage) {
        try (Client client = new Client()) {
            // Send the user message to the Gemini modelName and return a response
            return client.models.generateContent(
                    modelProperties.modelName(),
                    userMessage.text(),
                    GenerateContentConfig.builder()
                            .build()).text();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
