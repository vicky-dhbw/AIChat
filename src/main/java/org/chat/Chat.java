package org.chat;

public class Chat {
    private String sender;
    private String text;
    private String clientId;
    private String topic;

    public Chat() {

    }

    public Chat(String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.clientId = Configuration.INSTANCE.clientId;
        this.topic = "public chat";
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
