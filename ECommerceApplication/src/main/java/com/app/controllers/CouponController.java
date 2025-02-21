package com.app.controllers;


import com.app.entites.Coupon;
import com.app.payloads.CouponDTO;
import com.app.services.CouponService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/admin/coupons")
    public ResponseEntity<CouponDTO> createCoupon(@Valid @RequestBody Coupon coupon) {
        return new ResponseEntity<>(couponService.createCoupon(coupon), HttpStatus.CREATED);
    }

    @PutMapping("/admin/coupons/{id}")
    public ResponseEntity<CouponDTO> updateCoupon(@PathVariable Long id, @Valid @RequestBody Coupon coupon) {
        return new ResponseEntity<>(couponService.updateCoupon(id, coupon), HttpStatus.OK);
    }

    @PutMapping("/admin/coupons/{id}/activate")
    public ResponseEntity<CouponDTO> activateCoupon(@PathVariable Long id) {
        return new ResponseEntity<>(couponService.activateCoupon(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/public/validate/{code}")
    public ResponseEntity<CouponDTO> validateCoupon(@PathVariable String code) {
        return new ResponseEntity<>(couponService.validateCoupon(code), HttpStatus.OK);
    }
}