package com.example.demogcppubsub.services;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "nexusOutputChannel")
public interface PubsubOutboundGateway {
    void sendToPubsub(String text);
}
