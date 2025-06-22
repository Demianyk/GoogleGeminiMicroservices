package dev.ddemianyk.geminiai.telegram.service.ai;

import dev.ddemianyk.geminiai.common.domain.UserMessage;

public interface TelegramMessageToAiAgentMessageDeliveryService {
    String getAiResponse(UserMessage userMessage);
}
