package com.app.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private Long id;
    private String code;
    private Double discountAmount;
    private Double minimumPurchaseAmount;
    private Integer maxUsage;
    private Integer currentUsage;
    private Boolean isActive;
}
