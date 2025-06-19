package dev.ddemianyk.geminiai.ms.agent;

import dev.ddemianyk.geminiai.gemini.services.config.ModelProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan
@EnableConfigurationProperties(ModelProperties.class)
@ComponentScan(basePackages = {
        "dev.ddemianyk.geminiai.gemini",
        "dev.ddemianyk.geminiai.ms.agent"
})
public class AIAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIAgentApplication.class, args);
    }

}