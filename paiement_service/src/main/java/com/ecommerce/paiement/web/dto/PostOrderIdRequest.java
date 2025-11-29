package com.ecommerce.paiement.web.dto;

import jakarta.validation.constraints.NotNull;

public class PostOrderIdRequest {
    @NotNull
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }
}
