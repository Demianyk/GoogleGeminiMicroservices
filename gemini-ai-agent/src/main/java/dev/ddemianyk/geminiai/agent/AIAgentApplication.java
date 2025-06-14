package dev.ddemianyk.geminiai.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class AIAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIAgentApplication.class, args);
    }

    @RestController
    class MessageController {
        @GetMapping("/api/message")
        public String getMessage() {
            return "Hello from MS1!";
        }
    }
}