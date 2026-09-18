package com.ss11hw02lamgiahuyptit015.event;

import com.ss11hw02lamgiahuyptit015.dto.OrderItemDto;
import com.ss11hw02lamgiahuyptit015.dto.OrderRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderEvent(
        String eventId,
        String eventName,
        String orderId,
        String customerId,
        List<OrderItemDto> items,
        BigDecimal totalAmount,
        String shippingAddress,
        String status,
        Instant timestamp
) {
    public static final String EVENT_ORDER_CREATED = "order.created";

    public static OrderEvent createOrderCreatedEvent(String orderId, OrderRequest request) {
        return new OrderEvent(
                UUID.randomUUID().toString(),
                EVENT_ORDER_CREATED,
                orderId,
                request.customerId(),
                request.items(),
                request.totalAmount(),
                request.shippingAddress(),
                "CREATED",
                Instant.now()
        );
    }
}
