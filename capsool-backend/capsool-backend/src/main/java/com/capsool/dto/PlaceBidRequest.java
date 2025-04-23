package com.capsool.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class PlaceBidRequest {

    @NotNull(message = "Order ID cannot be null")
    private Long orderId;

    @NotNull(message = "Pharmacy ID cannot be null")
    private Long pharmacyId;

    @NotNull(message = "Bid Amount cannot be null")
    @Min(value = 1, message = "Bid Amount must be greater than 0")
    private Double bidAmount;
}
