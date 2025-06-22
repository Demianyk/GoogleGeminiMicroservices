package dev.ddemianyk.geminiai.telegram.service.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class TelegramMessageSender {

    private final TelegramClient telegramClient;

    private final static int MAX_MESSAGE_LENGTH = 4096; // Telegram's maximum message length
    void sendMessage(String chatId, String message) {

        if (message.length() <= MAX_MESSAGE_LENGTH) {
            sendChunk(chatId, message);
        } else {
            int start = 0;
            while (start < message.length()) {
                int end = Math.min(start + MAX_MESSAGE_LENGTH, message.length());
                String chunk = message.substring(start, end);
                sendChunk(chatId, chunk);
                start = end;
            }
        }
    }

    private void sendChunk(String chatId, String chunk) {
         SendMessage sendMessage = new SendMessage(chatId, chunk);
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to send message chunk", e);
        }
    }
}
