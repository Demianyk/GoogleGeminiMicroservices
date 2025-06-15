package dev.ddemianyk.geminiai.agent.api;

import dev.ddemianyk.geminiai.agent.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AIAgentController {

    private final AiService aiService;
    @PostMapping("/generate")
    public String getMessage(@RequestBody String userMessage) {
        return aiService.generate(userMessage);
    }
}
