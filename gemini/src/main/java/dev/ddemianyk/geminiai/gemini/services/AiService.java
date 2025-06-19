package dev.ddemianyk.geminiai.gemini.services;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.generators.ImageUploadingResponseGenerator;
import dev.ddemianyk.geminiai.gemini.services.generators.TextMessageResponseGenerator;
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
