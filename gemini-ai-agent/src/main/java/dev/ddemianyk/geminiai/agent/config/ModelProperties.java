package dev.ddemianyk.geminiai.agent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "model")
public
record ModelProperties(String modelName) {
}
