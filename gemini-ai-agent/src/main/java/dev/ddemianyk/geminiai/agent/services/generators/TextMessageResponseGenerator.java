package dev.ddemianyk.geminiai.agent.services.generators;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import dev.ddemianyk.geminiai.agent.config.ModelProperties;
import dev.ddemianyk.geminiai.agent.model.UserMessage;
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
