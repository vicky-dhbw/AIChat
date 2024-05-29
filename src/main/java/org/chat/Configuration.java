package org.chat;

import java.util.UUID;

public enum Configuration {
    INSTANCE;

    public final String mqttBrokerIP = "10.50.12.150";
    public final String chatSendReceiveTopic = "/aichat/system";
    public final String clientStateTopic = "/aichat/clientstate";
    public final String clientId = UUID.randomUUID().toString();
}
