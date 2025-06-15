package dev.ddemianyk.geminiai.agent;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import dev.ddemianyk.geminiai.agent.config.ModelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ModelProperties modelProperties;

    public String generate(String userMessage) {
        try (Client client = new Client()) {
            // Send the user message to the Gemini modelName and return a response
            return client.models.generateContent(
                    modelProperties.modelName(),
                    userMessage,
                    GenerateContentConfig.builder()
                            .build()).text();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
