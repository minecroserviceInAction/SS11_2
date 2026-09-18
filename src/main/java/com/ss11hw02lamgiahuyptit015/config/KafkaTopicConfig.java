package com.ss11hw02lamgiahuyptit015.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topics.order-events:storex-order-events}")
    private String topicName;

    @Value("${app.kafka.topics.order-events-partitions:5}")
    private int partitions;

    @Bean
    public NewTopic orderEventsTopic() {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(1)
                .build();
    }
}
