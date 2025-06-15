package dev.ddemianyk.geminiai.telegram.service.ai;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.ddemianyk.geminiai.telegram.model.UserMessage;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Objects;

@Service
public class AiAgentService {

    private final EurekaClient eurekaClient;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public AiAgentService(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public String generate(UserMessage userMessage) {
        return b(userMessage);
    }

    private String getAiAgentServiceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("gemini-ai-agent", false);
        return instance.getHomePageUrl();
    }

    private String a(UserMessage userMessage) {
        try {
            var baseUrl = getAiAgentServiceUrl();

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "api/generate"))
                    .POST(HttpRequest.BodyPublishers.ofString(userMessage.text()))
                    .build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to call gemini-ai-agent: " + e.getMessage();
        }
    }

    private String b(UserMessage userMessage) {
        // Create multipart body
        var requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("text", userMessage.text());
        var picture = userMessage.picture();
        if (Objects.nonNull(picture) && picture.length > 0) {
            requestBodyBuilder
                    .addFormDataPart("image", "image.jpg",
                            RequestBody.create(userMessage.picture(), MediaType.parse("image/jpeg")));
        }

        // Build the request
        var baseUrl = getAiAgentServiceUrl();
        Request request = new Request.Builder()
                .url(baseUrl + "api/generate-2")
                .post(requestBodyBuilder.build())
                .build();
        try {
            return new OkHttpClient().newCall(request).execute().body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}