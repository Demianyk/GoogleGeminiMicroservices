package dev.ddemianyk.geminiai.ms.telegrambot.service.ai;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.ddemianyk.geminiai.common.domain.UserMessage;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Service;
import dev.ddemianyk.geminiai.telegram.service.ai.AiAgentService;

import java.io.IOException;
import java.util.Objects;

@Service
public class RestSendingAiAgentService implements AiAgentService {

    private final EurekaClient eurekaClient;

    public RestSendingAiAgentService(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public String generate(UserMessage userMessage) {
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

    private String getAiAgentServiceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("gemini-ai-agent", false);
        return instance.getHomePageUrl();
    }
}