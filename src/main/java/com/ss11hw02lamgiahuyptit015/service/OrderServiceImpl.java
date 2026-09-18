package com.ss11hw02lamgiahuyptit015.service;

import com.ss11hw02lamgiahuyptit015.dto.OrderRequest;
import com.ss11hw02lamgiahuyptit015.event.OrderEvent;
import com.ss11hw02lamgiahuyptit015.producer.OrderEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderEventProducer orderEventProducer;

    public OrderServiceImpl(OrderEventProducer orderEventProducer) {
        this.orderEventProducer = orderEventProducer;
    }

    @Override
    public Mono<String> createOrder(OrderRequest request) {
        // 1. Khởi tạo mã đơn hàng duy nhất (orderId)
        String orderId = "ORD-" + UUID.randomUUID();
        log.info("Processing order draft creation with generated orderId: [{}]", orderId);

        // 2. Đóng gói sự kiện mang tên 'order.created'
        OrderEvent orderEvent = OrderEvent.createOrderCreatedEvent(orderId, request);

        // 3. Đẩy sự kiện vào Kafka bất đồng bộ với key là orderId (giải quyết BUG-03)
        // và trả về ngay lập tức orderId dưới dạng Mono<String>
        return orderEventProducer.sendOrderCreatedEvent(orderId, orderEvent)
                .thenReturn(orderId)
                .doOnSuccess(id -> log.info("Order [{}] successfully accepted and event produced to Kafka", id));
    }
}
