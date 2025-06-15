package dev.ddemianyk.geminiai.agent.api;

import dev.ddemianyk.geminiai.agent.model.UserMessage;
import dev.ddemianyk.geminiai.agent.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AIAgentController {

    private final AiService aiService;
    @PostMapping("/generate")
    public String getMessage(@RequestBody String userMessage) {
        return aiService.generate(new UserMessage(userMessage, new byte[0]));
    }
    @PostMapping(path = "/generate-2", consumes = "multipart/form-data")
    public String getMessage2(@RequestPart(value = "text", required = false) String text,
                              @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        return aiService.generate(new UserMessage(text, Objects.nonNull(imageFile) ? imageFile.getBytes() : new byte[0]));
    }
}
