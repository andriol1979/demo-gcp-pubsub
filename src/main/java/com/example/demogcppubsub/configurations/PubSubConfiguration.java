package com.example.demogcppubsub.configurations;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Configuration
public class PubSubConfiguration {
    @Value("${gcp.pubsub.topic}")
    private String pubsubTopicName;

    @Bean
    @ServiceActivator(inputChannel = "nexusOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, pubsubTopicName);
    }
}
