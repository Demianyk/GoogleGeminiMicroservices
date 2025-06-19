package dev.ddemianyk.geminiai.gemini.services.generators;


import dev.ddemianyk.geminiai.common.domain.UserMessage;

public interface ResponseGenerator {
    String generate(UserMessage userMessage);
}
