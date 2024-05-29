package org.chat;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

public class App {
    public static void main(String[] args) {
        ChatUI chatUI = new ChatUI();

        Mqtt3AsyncClient client = MqttClient.builder()
                .useMqttVersion3()
                .serverHost(Configuration.INSTANCE.mqttBrokerIP)
                .serverPort(1883)
                .identifier(Configuration.INSTANCE.clientId)
                .willPublish(Mqtt3Publish.builder()
                        .topic(Configuration.INSTANCE.clientStateTopic)
                        .payload(("Client: " + Configuration.INSTANCE.clientId + " going offline.").getBytes())
                        .build())
                .buildAsync();

        client.connect()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error: " + throwable.getMessage());
                    } else {
                        System.out.println("Connected successfully");
                        publishClientState(client, "Client: " + Configuration.INSTANCE.clientId + " is now online.");
                        Thread receiveServiceThread = new Thread(new ReceiveChatService(client, chatUI.getTextArea()));
                        receiveServiceThread.start();
                    }
                });

        SendChatService sendChatService = new SendChatService(client);

        chatUI.getSendButton().addActionListener(e -> {
            String user = chatUI.getNameField().getText().trim();
            String message = chatUI.getTextField().getText().trim();
            if (!user.isEmpty() && !message.isEmpty()) {
                sendChatService.sendMessage(user, message);
                chatUI.getTextField().setText("");
            }
        });
    }

    private static void publishClientState(Mqtt3AsyncClient client, String message) {
        client.publishWith()
                .topic(Configuration.INSTANCE.clientStateTopic)
                .payload(message.getBytes())
                .send()
                .whenComplete((publishAck, pubThrowable) -> {
                    if (pubThrowable != null) {
                        System.out.println("Failed to publish client state: " + pubThrowable.getMessage());
                    } else {
                        System.out.println("Client state published successfully.");
                    }
                });
    }
}
