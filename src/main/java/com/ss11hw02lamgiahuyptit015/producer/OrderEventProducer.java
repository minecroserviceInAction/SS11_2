package com.ss11hw02lamgiahuyptit015.producer;

import com.ss11hw02lamgiahuyptit015.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OrderEventProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicName;

    public OrderEventProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${app.kafka.topics.order-events:storex-order-events}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    /**
     * BẮT BUỘC (BUG-03 FIX): Topic storex-order-events có 5 Partitions.
     * Khi gọi send(), bắt buộc phải chỉ định orderId làm Key phân tuyến (partition routing key).
     * Thuật toán băm murmur2 của Kafka sẽ đảm bảo mọi event thuộc cùng một orderId luôn rơi
     * vào cùng 1 Partition duy nhất, bảo toàn trật tự xử lý (Order Preservation).
     */
    public Mono<SendResult<String, Object>> sendOrderCreatedEvent(String orderId, OrderEvent event) {
        return Mono.fromFuture(() -> {
            log.info("Emitting event [{}] to Kafka topic [{}] with Partition Key (orderId): [{}]",
                    event.eventName(), topicName, orderId);
            return kafkaTemplate.send(topicName, orderId, event);
        }).doOnSuccess(result -> {
            var metadata = result.getRecordMetadata();
            log.info("Kafka send success: orderId=[{}] routed to Partition=[{}] at Offset=[{}]",
                    orderId, metadata.partition(), metadata.offset());
        }).doOnError(error -> {
            log.error("Kafka send failed for orderId=[{}]: {}", orderId, error.getMessage(), error);
        });
    }

    public String getTopicName() {
        return topicName;
    }
}
