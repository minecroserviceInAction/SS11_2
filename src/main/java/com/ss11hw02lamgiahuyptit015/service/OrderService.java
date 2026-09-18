package com.ss11hw02lamgiahuyptit015.service;

import com.ss11hw02lamgiahuyptit015.dto.OrderRequest;
import reactor.core.publisher.Mono;

public interface OrderService {

    /**
     * Tiếp nhận yêu cầu đặt hàng, tạo orderId, phát sự kiện order.created
     * vào Kafka topic và trả về orderId dưới dạng Mono<String>.
     *
     * @param request Payload chứa thông tin đơn hàng
     * @return Mono<String> chứa mã đơn hàng orderId
     */
    Mono<String> createOrder(OrderRequest request);
}
