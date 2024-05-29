package org.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;

import javax.swing.*;

public class ReceiveChatService implements Runnable {

    private final Mqtt3AsyncClient mqtt3AsyncClient;
    private final JTextArea textArea;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ReceiveChatService(Mqtt3AsyncClient mqtt3AsyncClient, JTextArea textArea) {
        this.mqtt3AsyncClient = mqtt3AsyncClient;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        mqtt3AsyncClient.subscribeWith()
                .topicFilter(Configuration.INSTANCE.chatSendReceiveTopic)
                .callback(this::handleMessage)
                .send();
    }

    private void handleMessage(Mqtt3Publish publish) {
        String message = new String(publish.getPayloadAsBytes());
        try {
            Chat chat = objectMapper.readValue(message, Chat.class);
            SwingUtilities.invokeLater(() -> textArea.append("Received message from " + chat.getSender() + " : " + chat.getText() + "\n"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}