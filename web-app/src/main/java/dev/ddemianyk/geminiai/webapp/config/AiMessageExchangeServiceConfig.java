package dev.ddemianyk.geminiai.webapp.config;

import com.netflix.discovery.EurekaClient;
import dev.ddemianyk.geminiai.common.service.ai.AiAgentMessageExchangeService;
import dev.ddemianyk.geminiai.service.ai.RestSendingAiAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AiMessageExchangeServiceConfig {
    private final EurekaClient eurekaClient;

    @Bean
    AiAgentMessageExchangeService aiAgentMessageExchangeService() {
        return new RestSendingAiAgentService(eurekaClient);
    }
}
