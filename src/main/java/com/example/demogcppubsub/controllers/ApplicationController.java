package com.example.demogcppubsub.controllers;

import com.example.demogcppubsub.services.PubsubOutboundGateway;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
public class ApplicationController {

    private static final Logger LOGGER = LoggerFactory.getLogger (ApplicationController.class);

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    private PubsubOutboundGateway messagingGateway;

    @Value("${gcp.pubsub.subscription}")
    private String pubsubSubscriptionName;

    @PostMapping("gcp-pubsub/message")
    public ResponseEntity<String> publish(@RequestBody String message) {
        LOGGER.info("Message: " + message);
        messagingGateway.sendToPubsub(message);

        return ResponseEntity.ok("Published message successfully");
    }

    //Testing purpose only
    @GetMapping("gcp-pubsub/message")
    public ResponseEntity<String> publish() {
        Subscriber subscriber = pubSubTemplate.subscribe(pubsubSubscriptionName, (message) -> {
            LOGGER.info("Message received from " + pubsubSubscriptionName + " subscription: "
                    + message.getPubsubMessage().getData().toStringUtf8());
            message.ack();
        });

        return ResponseEntity.ok("Received message successfully");
    }
}
