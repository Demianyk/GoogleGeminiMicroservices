package dev.ddemianyk.geminiai.telegram.service.bot;

import dev.ddemianyk.geminiai.telegram.service.ai.TelegramMessageToAiAgentMessageDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CommandProcessor {

    private final TelegramMessageToAiAgentMessageDeliveryService telegramMessageToAiAgentMessageDeliveryService;
    private final TelegramMessageSender telegramMessageSender;

    boolean isCommand(Update update) {
        // Check if the message starts with a command prefix (e.g., "/")
        return update.hasMessage() && update.getMessage().getText().startsWith("/");
    }

    public void processCommand(Update update) {
        // Process the command and return a response
        String command = update.getMessage().getText().substring(1).split(" ")[0].trim();
        switch (command.toLowerCase()) {
            case "clear" -> {
                telegramMessageToAiAgentMessageDeliveryService.clearChatHistory(update.getMessage().getFrom().getId());
                telegramMessageSender.sendMessage(
                        update.getMessage().getChatId(),
                        "Chat history cleared. Feel free to start a fresh conversation!"
                );
                return;
            }
            default -> {
                return;
            }
        }
    }
}
