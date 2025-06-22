package dev.ddemianyk.geminiai.telegram.service.bot;

import dev.ddemianyk.geminiai.telegram.service.ai.TelegramMessageToAiAgentMessageDeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramMessageToAiAgentMessageDeliveryService telegramMessageToAiAgentMessageDeliveryService;
    private final TelegramMessageSender telegramClient;
    private final UserMessageTransformer userMessageTransformer;

    @Override
    public void consume(Update update) {
        try {
            var userMessageOpt = userMessageTransformer.updateToUserMessage(update);
            if (!userMessageOpt.isPresent()) {
                log.info("Received empty message, skipping processing.");
                return;
            }
            userMessageOpt.ifPresent(userMessage -> {
                var aiResponse = telegramMessageToAiAgentMessageDeliveryService.getAiResponse(userMessage);
                log.info("Received message: {}", userMessage.text());
                Long chatId = update.getMessage().getChatId();
                telegramClient.sendMessage(chatId.toString(), aiResponse);
            });

        } catch (Exception e) {
            log.error("Error processing update: {}", update, e);
        }
    }
}
