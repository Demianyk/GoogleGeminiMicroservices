package dev.ddemianyk.geminiai.gemini.services;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.content.ContentManager;
import dev.ddemianyk.geminiai.gemini.services.generators.ImageUploadingResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {
    private final ImageUploadingResponseGenerator imageUploadingResponseGenerator;
    private final ContentManager contentManager;

    public String generate(UserMessage userMessage) {
        if (userMessage.clearChatHistory()) {
            contentManager.clearContent(userMessage.userId());
            return "Chat history cleared. Feel free to start a fresh conversation!";
        }
        return imageUploadingResponseGenerator.generate(userMessage);
    }
}
