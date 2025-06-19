package dev.ddemianyk.geminiai.telegram.service.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import dev.ddemianyk.geminiai.telegram.domain.TelegramCredentials;

@Component
@RequiredArgsConstructor
public class TelegramBotInitializer {
    private final TelegramBot telegramBot;
    private final TelegramCredentials telegramCredentials;
    private final TelegramBotsLongPollingApplication botsApplication;


    @PostConstruct
    public void start() {
        try {
            botsApplication.registerBot(telegramCredentials.apiToken(), telegramBot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
