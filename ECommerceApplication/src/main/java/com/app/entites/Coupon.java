package com.app.entites;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 5, message = "Coupon code must contain atleast 5 characters")
    private String code;

    @NotBlank
    @Min(1)
    @Max(99)
    private Double discountPercentage;

    private Boolean isActive = true;

    @OneToMany(mappedBy = "coupon")
    private List<Product> eligibleProducts = new ArrayList<>();
}
