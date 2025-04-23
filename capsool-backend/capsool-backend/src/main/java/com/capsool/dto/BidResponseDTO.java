package com.capsool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidResponseDTO {
    private Long bidId;
    private Long orderId;
    private Long pharmacyId;
    private String pharmacyName;
    private Double bidAmount;
    private boolean winner;
}
