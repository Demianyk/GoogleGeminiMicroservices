package dev.ddemianyk.geminiai.telegram.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.*;

@Service
public class AiAgentService {

    private final EurekaClient eurekaClient;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public AiAgentService(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public String fetchMessageFromMs1() {
        try {
            InstanceInfo instance = eurekaClient
                .getApplication("gemini-ai-agent".toUpperCase())
                .getInstances()
                .get(0);

            String baseUrl = instance.getHomePageUrl();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "api/message"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to call gemini-ai-agent: " + e.getMessage();
        }
    }
}