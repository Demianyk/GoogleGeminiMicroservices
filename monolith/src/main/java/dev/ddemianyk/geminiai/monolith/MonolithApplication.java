package dev.ddemianyk.geminiai.monolith;

import dev.ddemianyk.geminiai.gemini.services.config.ModelProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties(ModelProperties.class)
@ComponentScan(basePackages = {
        "dev.ddemianyk.geminiai.gemini",
        "dev.ddemianyk.geminiai.monolith",
        "dev.ddemianyk.geminiai.telegram",
        "dev.ddemianyk.geminiai.web",
})
public class MonolithApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonolithApplication.class, args);
    }

}