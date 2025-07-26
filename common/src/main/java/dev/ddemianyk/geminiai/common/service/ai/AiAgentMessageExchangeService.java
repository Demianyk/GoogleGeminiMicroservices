package dev.ddemianyk.geminiai.common.service.ai;

import dev.ddemianyk.geminiai.common.domain.UserMessage;

public interface AiAgentMessageExchangeService {
    String getAiResponse(UserMessage userMessage);

    void clearChatHistory(Long userId);
}
