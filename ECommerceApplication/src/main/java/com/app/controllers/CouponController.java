package com.app.controllers;


import com.app.payloads.CouponDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
@SecurityRequirement(name = "E-Commerce Application")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/admin")
    public ResponseEntity<CouponDTO> createCoupon(@Valid @RequestBody CouponDTO couponDTO) {
        return new ResponseEntity<>(couponService.createCoupon(couponDTO), HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<CouponDTO> updateCoupon(@PathVariable Long id, @Valid @RequestBody CouponDTO couponDTO) {
        return new ResponseEntity<>(couponService.updateCoupon(id, couponDTO), HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/public/validate/{code}")
    public ResponseEntity<CouponDTO> validateCoupon(@PathVariable String code, @RequestParam String email) {
        return new ResponseEntity<>(couponService.validateCoupon(code, email), HttpStatus.OK);
    }
}