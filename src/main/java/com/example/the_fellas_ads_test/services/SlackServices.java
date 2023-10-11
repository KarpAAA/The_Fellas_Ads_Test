package com.example.the_fellas_ads_test.services;


import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackServices {
    @Value("${slack.token}")
    private String slackToken;

    public void sendMessageToSlack(String channel, String message) {
        Slack slack = Slack.getInstance();
        MethodsClient methods = slack.methods(slackToken);

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channel)
                .text(message)
                .build();

        ChatPostMessageResponse response = null;
        try {
            response = methods.chatPostMessage(request);
        } catch (IOException | SlackApiException e) {
            throw new RuntimeException(e);
        }

        if (response.isOk()) {
            System.out.println("Message was sent to Slack.");
        } else {
            System.err.println("Error during sending message in Slack: " + response.getError());
        }
    }
}
