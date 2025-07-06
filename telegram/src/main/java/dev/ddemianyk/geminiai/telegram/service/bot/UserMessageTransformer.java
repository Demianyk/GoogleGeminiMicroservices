package dev.ddemianyk.geminiai.telegram.service.bot;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMessageTransformer {

    private final TelegramClient telegramClient;

    Optional<UserMessage> updateToUserMessage(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return Optional.empty();
        }

        var telegramMessage = update.getMessage();
        if (telegramMessage.getText().startsWith("/clear")) {
            return Optional.of(clearUserContentRequest(telegramMessage.getFrom().getId()));
        }

        var stringBuilder = new StringBuilder();
        if (telegramMessage.hasCaption()) {
            stringBuilder.append(telegramMessage.getCaption()).append(". ");
        }
        if (telegramMessage.hasText()) {
            stringBuilder.append(telegramMessage.getText());
        }
        return Optional.of(new UserMessage(
                telegramMessage.getFrom().getId(),
                stringBuilder.toString(),
                getPhotoFromUpdate(update)
        ));
    }

    public byte[] getPhotoFromUpdate(Update update) {
        if (update == null || !update.hasMessage() || !update.getMessage().hasPhoto()) {
            return null;
        }

        PhotoSize photo = update.getMessage().getPhoto().get(0);

        try {
            if (photo == null) return null;
            // Step 1: Get the File object
            GetFile getFile = new GetFile(photo.getFileId());
            File file = telegramClient.execute(getFile);

            // Step 2: Download the file as InputStream
            try (InputStream inputStream = telegramClient.downloadFileAsStream(file);
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                byte[] data = new byte[8192];
                int nRead;
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                return buffer.toByteArray();
            }
        } catch (IOException | TelegramApiException e) {
            throw new RuntimeException("Error downloading photo from Telegram: " + e.getMessage(), e);
        }
    }

    private static UserMessage clearUserContentRequest(Long userId) {
        return new UserMessage(
                userId,
                "",
                new byte[0],
                true
        );
    }
}
