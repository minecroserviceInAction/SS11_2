package com.ss11hw02lamgiahuyptit015.controller;

import com.ss11hw02lamgiahuyptit015.dto.OrderRequest;
import com.ss11hw02lamgiahuyptit015.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint tiếp nhận yêu cầu đặt hàng bất đồng bộ.
     * Trả về HTTP Status 202 Accepted và Mono<String> chứa mã đơn hàng orderId.
     *
     * @param request Payload thông tin đơn hàng dạng JSON
     * @return Mono<String> mã đơn hàng được chấp nhận xử lý ngầm
     */
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<String> createOrder(@Valid @RequestBody OrderRequest request) {
        log.info("Received order creation request for customerId: [{}]", request.customerId());
        return orderService.createOrder(request);
    }
}
