package com.app.services;

import com.app.entites.Coupon;
import com.app.payloads.CouponDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public CouponDTO validateCoupon(String code, String email) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "code", code));

        if (!coupon.getIsActive()) {
            throw new APIException("Coupon is inactive");
        }

        if (LocalDate.now().isBefore(coupon.getValidFrom()) ||
                LocalDate.now().isAfter(coupon.getValidUntil())) {
            throw new Api("Coupon is not valid at this time");
        }

        if (coupon.getCurrentUsage() >= coupon.getMaxUsage()) {
            throw new APIException("Coupon usage limit exceeded");
        }

        // Check if user has already used this coupon
        if (couponRepository.hasUserUsedCoupon(email, coupon.getId())) {
            throw new APIException("You have already used this coupon");
        }

        return modelMapper.map(coupon, CouponDTO.class);
    }

}
