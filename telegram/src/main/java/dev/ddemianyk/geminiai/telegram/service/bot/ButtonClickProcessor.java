package dev.ddemianyk.geminiai.telegram.service.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class ButtonClickProcessor {
    private final BotActionsHelper botActionsHelper;

    public boolean containsCallbackData(Update update) {
        return update.hasCallbackQuery();
    }

    public void processCallbackData(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        switch (callbackData) {
            case "clear_chat_history" ->
                    botActionsHelper.clearChatHistory(update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getMessage().getChatId());
            default -> log.warn("Unknown callback data: " + callbackData);
        }
    }
}
