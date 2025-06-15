package dev.ddemianyk.geminiai.telegram.service.bot;

import ch.qos.logback.classic.Logger;
import dev.ddemianyk.geminiai.telegram.service.ai.AiAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");

    private final AiAgentService aiAgentService;
    private final TelegramClient telegramClient = new OkHttpTelegramClient(BOT_TOKEN);

    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void consume(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                var aiResponse = aiAgentService.generate(messageText);
                Long chatId = update.getMessage().getChatId();
                SendMessage sendMessage = new SendMessage(chatId.toString(), aiResponse);
                telegramClient.execute(sendMessage);
                // Echo the received message back to the user
                log.info("Received message: {}", messageText);
            }
        } catch (Exception e) {
            log.error("Error processing update: {}", update, e);
        }
    }
}
