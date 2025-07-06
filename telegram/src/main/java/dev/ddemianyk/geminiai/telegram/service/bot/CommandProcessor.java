package dev.ddemianyk.geminiai.telegram.service.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class CommandProcessor {

    private final BotActionsHelper botActionsHelper;
    private final TelegramMessageSender telegramMessageSender;
    private final TelegramClient telegramClient;

    boolean isCommand(Update update) {
        // Check if the message starts with a command prefix (e.g., "/")
        return update.hasMessage() && update.getMessage().getText().startsWith("/");
    }

    public void processCommand(Update update) {
        // Process the command and return a response
        String command = update.getMessage().getText().substring(1).split(" ")[0].trim();
        switch (command.toLowerCase()) {
            case "clear" ->
                    botActionsHelper.clearChatHistory(update.getMessage().getFrom().getId(), update.getMessage().getChatId());
            case "start" -> botActionsHelper.showMainMenu(update);
            default -> botActionsHelper.handleUnknownCommand(update, command);
        }
    }
}
