package dev.ddemianyk.geminiai.web.controller;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.common.service.ai.AiAgentMessageExchangeService;
import dev.ddemianyk.geminiai.web.model.ApiMessage;
import dev.ddemianyk.geminiai.web.utils.UserInfoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiApiController {
    private final AiAgentMessageExchangeService aiAgentMessageExchangeService;

    @PostMapping("/send-message")
    public ApiMessage sendMessage(@RequestBody ApiMessage message, Principal principal) {
        String text = aiAgentMessageExchangeService.getAiResponse(new UserMessage(
                UserInfoUtils.getUserId(principal),
                message.message(),
                null
        ));
        return new ApiMessage(text);
    }

    @DeleteMapping("/clear-history")
    public void clearHistory(Principal principal) {
        aiAgentMessageExchangeService.clearChatHistory(UserInfoUtils.getUserId(principal));
    }
}
