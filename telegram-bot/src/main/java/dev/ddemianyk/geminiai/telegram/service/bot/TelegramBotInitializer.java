package dev.ddemianyk.geminiai.telegram.service.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Component
public class TelegramBotInitializer {
    private final TelegramBot telegramBot;
    private final TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

    public TelegramBotInitializer(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void start() {
        try {
            botsApplication.registerBot(telegramBot.getBotToken(), telegramBot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
