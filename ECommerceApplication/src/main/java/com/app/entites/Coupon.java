package com.app.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private Double discountAmount;
    private Double minimumPurchaseAmount;

    private Integer maxUsage;
    private Integer currentUsage;

    private Boolean isActive;

    @OneToMany(mappedBy = "coupon")
    private List<CouponUsage> usages = new ArrayList<>();
}
