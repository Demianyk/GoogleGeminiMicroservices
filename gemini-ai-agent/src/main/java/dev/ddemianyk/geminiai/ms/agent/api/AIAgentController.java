package dev.ddemianyk.geminiai.ms.agent.api;

import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.gemini.services.AiService;
import lombok.RequiredArgsConstructor;
import static org.apache.hc.core5.util.TextUtils.isEmpty;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping(path = "/generate", consumes = "multipart/form-data")
    public String generate(
            @RequestPart(value = "userId") String userId,
            @RequestPart(value = "text", required = false) String text,
            @RequestPart(value = "clearChatHistory", required = false) String clearChatHistory,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        return aiService
                .generate(
                        new UserMessage(
                                Long.parseLong(userId),
                                text,
                                Objects.nonNull(imageFile) ? imageFile.getBytes() : new byte[0],
                                !isEmpty(clearChatHistory) && Boolean.parseBoolean(clearChatHistory)
                        )
                );
    }
}
