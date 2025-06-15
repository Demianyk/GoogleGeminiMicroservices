package dev.ddemianyk.geminiai.agent.services.generators;

import dev.ddemianyk.geminiai.agent.model.UserMessage;

public interface ResponseGenerator {
    String generate(UserMessage userMessage);
}
