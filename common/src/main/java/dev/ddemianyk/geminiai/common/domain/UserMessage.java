package dev.ddemianyk.geminiai.common.domain;

public record UserMessage(Long userId,
                          String text,
                          byte[] picture,
                          boolean clearChatHistory) {
    public UserMessage(Long userId, String text, byte[] picture) {
        this(userId, text, picture, false);
    }
}
