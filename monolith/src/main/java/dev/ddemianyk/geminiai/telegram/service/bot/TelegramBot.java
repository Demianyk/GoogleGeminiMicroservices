package dev.ddemianyk.geminiai.telegram.service.bot;

import dev.ddemianyk.geminiai.telegram.service.ai.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private final AiService aiService;
    private final TelegramClient telegramClient;
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
                var aiResponse = aiService.generate(userMessage);
                log.info("Received message: {}", userMessage.text());
                Long chatId = update.getMessage().getChatId();
                SendMessage sendMessage = new SendMessage(chatId.toString(), aiResponse);
                try {
                    telegramClient.execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            log.error("Error processing update: {}", update, e);
        }
    }
}
