package com.ss11hw02lamgiahuyptit015.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        @NotBlank(message = "Customer ID cannot be blank")
        String customerId,

        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemDto> items,

        @NotNull(message = "Total amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
        BigDecimal totalAmount,

        @NotBlank(message = "Shipping address cannot be blank")
        String shippingAddress,

        String paymentMethod
) {
}
