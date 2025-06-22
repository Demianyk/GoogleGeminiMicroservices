package dev.ddemianyk.geminiai.gemini.services;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.generators.ImageUploadingResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {
    private final ImageUploadingResponseGenerator imageUploadingResponseGenerator;

    public String generate(UserMessage userMessage) {
        return imageUploadingResponseGenerator.generate(userMessage);
    }
}
