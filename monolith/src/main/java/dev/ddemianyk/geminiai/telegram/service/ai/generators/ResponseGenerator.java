package dev.ddemianyk.geminiai.telegram.service.ai.generators;


import dev.ddemianyk.geminiai.telegram.model.UserMessage;

public interface ResponseGenerator {
    String generate(UserMessage userMessage);
}
