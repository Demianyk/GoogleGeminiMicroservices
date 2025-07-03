package dev.ddemianyk.geminiai.gemini.services;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.content.ChatManager;
import dev.ddemianyk.geminiai.gemini.services.generators.ImageUploadingResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {
    private final ImageUploadingResponseGenerator imageUploadingResponseGenerator;
    private final ChatManager chatManager;

    public String generate(UserMessage userMessage) {
        return imageUploadingResponseGenerator.generate(userMessage);
    }

    public void clearChatHistory(Long userId) {
        chatManager.clearChatHistory(userId);
    }
}
