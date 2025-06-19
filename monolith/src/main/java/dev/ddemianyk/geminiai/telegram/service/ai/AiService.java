package dev.ddemianyk.geminiai.telegram.service.ai;

import dev.ddemianyk.geminiai.telegram.model.UserMessage;
import dev.ddemianyk.geminiai.telegram.service.ai.generators.ImageUploadingResponseGenerator;
import dev.ddemianyk.geminiai.telegram.service.ai.generators.TextMessageResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AiService {
    private final TextMessageResponseGenerator textMessageResponseGenerator;
    private final ImageUploadingResponseGenerator imageUploadingResponseGenerator;

    public String generate(UserMessage userMessage) {
        return Objects.isNull(userMessage.picture()) || userMessage.picture().length == 0
                ? textMessageResponseGenerator.generate(userMessage)
                : imageUploadingResponseGenerator.generate(userMessage);
    }
}
