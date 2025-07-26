package dev.ddemianyk.geminiai.monolith.service.ai;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.common.service.ai.AiAgentMessageExchangeService;
import dev.ddemianyk.geminiai.gemini.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonolithAiService implements AiAgentMessageExchangeService {
    private final AiService aiService;

    public String getAiResponse(UserMessage userMessage) {
        return aiService.generate(userMessage);
    }

    @Override
    public void clearChatHistory(Long userId) {
        aiService.clearChatHistory(userId);
    }
}
