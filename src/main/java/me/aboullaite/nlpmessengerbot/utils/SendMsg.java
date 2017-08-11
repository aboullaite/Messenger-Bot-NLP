package me.aboullaite.nlpmessengerbot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.aboullaite.nlpmessengerbot.domain.MessageResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.ExecutorService;


public class SendMsg {

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String MESSAGE_POST_ENDPOINT_FORMAT = "https://graph.facebook.com/v2.6/me/messages?access_token=";
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;
    private final String accessToken;

    @Autowired
    public SendMsg (final OkHttpClient client, final ObjectMapper objectMapper, final ExecutorService executorService, final String accessToken) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.executorService = executorService;
        this.accessToken = accessToken;
    }


    private Runnable newPostRunnable (final MessageResponse msgRes) {
        return () -> {
            try {
                final String responseString = SendMsg.this.objectMapper.writeValueAsString(msgRes);

                RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, responseString);
                final Request request = new Request.Builder().url(MESSAGE_POST_ENDPOINT_FORMAT + SendMsg.this.accessToken).post(requestBody).build();
                final Response response = SendMsg.this.client.newCall(request).execute();
                System.out.println(response.toString());
            } catch (final IOException e) {
              e.printStackTrace();
            }

        };
    }

    public void sendMessage(MessageResponse response) {
        this.executorService.submit(this.newPostRunnable(response));
    }
}
