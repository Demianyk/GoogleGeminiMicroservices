package dev.ddemianyk.geminiai.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan
@EnableConfigurationProperties
public class AIAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIAgentApplication.class, args);
    }

}