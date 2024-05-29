package org.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.nio.charset.StandardCharsets;

public class SendChatService {
    private final Mqtt3AsyncClient mqtt3AsyncClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SendChatService(Mqtt3AsyncClient mqtt3AsyncClient) {
        this.mqtt3AsyncClient = mqtt3AsyncClient;
    }

    public void sendMessage(String name, String message) {
        try {
            Chat chat = new Chat(name, message);
            String payload = objectMapper.writeValueAsString(chat);
            mqtt3AsyncClient.publishWith()
                    .topic(Configuration.INSTANCE.chatSendReceiveTopic)
                    .payload(payload.getBytes(StandardCharsets.UTF_8))
                    .send()
                    .whenComplete((publish, error) -> {
                        if (error != null) {
                            System.out.println(error.getMessage());
                        } else {
                            System.out.println("Message sent successfully");
                        }
                    });
        } catch (Exception e) {
            System.out.println("Error serializing message: " + e.getMessage());
        }
    }
}
