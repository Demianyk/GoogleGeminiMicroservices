package dev.ddemianyk.geminiai.agent.model;

import java.io.File;

public record UserMessage(String text,
                          byte[] picture) {
}
