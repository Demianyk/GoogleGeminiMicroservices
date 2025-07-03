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
    private final TelegramMessageSender telegramMessageSender;
    private final UserMessageTransformer userMessageTransformer;
    private final CommandProcessor commandProcessor;

    @Override
    public void consume(Update update) {
        try {
            var userMessageOpt = userMessageTransformer.updateToUserMessage(update);
            if (userMessageOpt.isEmpty()) {
                log.info("Received empty message, skipping processing.");
                return;
            }
            if (commandProcessor.isCommand(update)) {
                commandProcessor.processCommand(update);
                return;
            }
            userMessageOpt.ifPresent(userMessage -> {
                var aiResponse = telegramMessageToAiAgentMessageDeliveryService.getAiResponse(userMessage);
                log.info("Received message: {}", userMessage.text());
                telegramMessageSender.sendMessage(update.getMessage().getChatId(), aiResponse);
            });

        } catch (Exception e) {
            log.error("Error processing update: {}", update, e);
        }
    }
}
