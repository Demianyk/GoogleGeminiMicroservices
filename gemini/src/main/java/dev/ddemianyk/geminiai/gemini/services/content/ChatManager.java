package dev.ddemianyk.geminiai.gemini.services.content;

import com.google.genai.Chat;
import com.google.genai.Client;
import dev.ddemianyk.geminiai.gemini.services.config.ModelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ChatManager {
    private final ModelProperties modelProperties;
    private final Map<Long, Chat> chatMap = new ConcurrentHashMap<>();

    public Chat getChat(Long userId) {
        return chatMap.computeIfAbsent(userId, this::newChat);
    }

    private Chat newChat(Long notUsed) {
        return new Client().chats.create(modelProperties.modelName());
    }

    public void clearChatHistory(Long userId) {
        chatMap.remove(userId);
    }
}
