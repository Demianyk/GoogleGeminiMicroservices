package dev.ddemianyk.geminiai.service.ai;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.ddemianyk.geminiai.common.domain.UserMessage;
import dev.ddemianyk.geminiai.common.service.ai.AiAgentMessageExchangeService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Objects;

public class RestSendingAiAgentService implements AiAgentMessageExchangeService {

    private final EurekaClient eurekaClient;

    public RestSendingAiAgentService(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public String getAiResponse(UserMessage userMessage) {
        // Create multipart body
        var requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", userMessage.userId().toString())
                .addFormDataPart("text", userMessage.text())
                .addFormDataPart("clearChatHistory", Boolean.toString(userMessage.clearChatHistory()));

        var picture = userMessage.picture();
        if (Objects.nonNull(picture) && picture.length > 0) {
            requestBodyBuilder
                    .addFormDataPart("image", "image.jpg",
                            RequestBody.create(userMessage.picture(), MediaType.parse("image/jpeg")));
        }

        // Build the request
        var baseUrl = getAiAgentServiceUrl();
        Request request = new Request.Builder()
                .url(baseUrl + "api/generate")
                .post(requestBodyBuilder.build())
                .build();
        try {
            return new OkHttpClient().newCall(request).execute().body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearChatHistory(Long userId) {
        // Create request to clear chat history
        var baseUrl = getAiAgentServiceUrl();
        Request request = new Request.Builder()
                .url(baseUrl + "api/clearChatHistory?userId=" + userId)
                .get()
                .build();
        try {
            new OkHttpClient().newCall(request).execute().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAiAgentServiceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("gemini-ai-agent", false);
        return instance.getHomePageUrl();
    }
}