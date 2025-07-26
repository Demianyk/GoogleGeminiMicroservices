package dev.ddemianyk.geminiai.telegram.service.bot;

import dev.ddemianyk.geminiai.common.service.ai.AiAgentMessageExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class BotActionsHelper {
    private final AiAgentMessageExchangeService aiAgentMessageExchangeService;
    private final TelegramMessageSender telegramMessageSender;
    private final TelegramClient telegramClient;

    void clearChatHistory(Long userId, Long chatId) {
        aiAgentMessageExchangeService.clearChatHistory(userId);
        telegramMessageSender.sendMessage(chatId, "Chat history cleared. Feel free to start a fresh conversation!");
    }

    void showMainMenu(Update update) {
        var message = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text("Menu:")
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboardRow(
                                new InlineKeyboardRow(InlineKeyboardButton
                                        .builder()
                                        .text("Clear Chat History")
                                        .callbackData("clear_chat_history")
                                        .build()
                                )
                        )
                        .build())
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    void handleUnknownCommand(Update update, String command) {
        String responseText = "Unknown command: " + command + ". Please use /start to see available commands.";
        SendMessage message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(responseText)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                .text("Main Menu")
                                .callbackData("main_menu")
                                .build()))
                        .build())
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
