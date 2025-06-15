package dev.ddemianyk.geminiai.telegram;

import dev.ddemianyk.geminiai.telegram.service.ai.AiAgentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class TelegramBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

    @RestController
    class ClientController {
        private final AiAgentService aiAgentService;

        public ClientController(AiAgentService aiAgentService) {
            this.aiAgentService = aiAgentService;
        }

        @GetMapping("/api/call-ms1")
        public String callMs1() {
            return aiAgentService.generate("a");
        }
    }
}