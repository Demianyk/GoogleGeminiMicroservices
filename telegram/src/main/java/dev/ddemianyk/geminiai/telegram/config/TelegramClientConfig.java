package dev.ddemianyk.geminiai.telegram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import dev.ddemianyk.geminiai.telegram.domain.TelegramCredentials;

@Configuration
public class TelegramClientConfig {

    @Bean
    TelegramCredentials telegramCredentials() {
        return new TelegramCredentials(System.getenv("TELEGRAM_BOT_TOKEN"));
    }

    @Bean
    TelegramClient telegramClient() {
        return new OkHttpTelegramClient(telegramCredentials().apiToken());
    }

    @Bean
    TelegramBotsLongPollingApplication telegramBotsLongPollingApplication() {
        return new TelegramBotsLongPollingApplication();
    }
}
