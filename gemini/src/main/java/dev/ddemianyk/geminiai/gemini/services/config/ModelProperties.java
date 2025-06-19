package dev.ddemianyk.geminiai.gemini.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "model")
public
record ModelProperties(String modelName) {
}
