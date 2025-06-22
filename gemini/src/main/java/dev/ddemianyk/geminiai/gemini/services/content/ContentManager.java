package dev.ddemianyk.geminiai.gemini.services.content;

import com.google.genai.types.Content;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ContentManager {
    private final Map<Long, List<Content>> contentMap = new ConcurrentHashMap<>();

    public void addContent(Long userId, Content content) {
        contentMap.computeIfAbsent(userId, k -> new java.util.ArrayList<>()).add(content);
    }

    public List<Content> getContent(Long userId) {
        return contentMap.getOrDefault(userId, List.of());
    }

    public void clearContent(Long userId) {
        contentMap.remove(userId);
    }

}
