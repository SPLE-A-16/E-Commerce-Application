package com.app.services;

import com.app.entites.Category;
import com.app.entites.Coupon;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.CategoryDTO;
import com.app.payloads.CouponDTO;
import com.app.repositories.CouponRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepo couponRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CouponDTO validateCoupon(String code) {
        Coupon coupon = couponRepository.findByCode(code);

        if (!coupon.getIsActive()) {
            throw new APIException("Coupon is inactive");
        }
        return modelMapper.map(coupon, CouponDTO.class);
    }

    @Override
    public CouponDTO createCoupon(Coupon coupon) {
        Coupon savedCoupon = couponRepository.findByCode(coupon.getCode());

        if (savedCoupon != null) {
            throw new APIException("Coupon with the code '" + coupon.getCode() + "' already exists !!!");
        }

        savedCoupon = couponRepository.save(coupon);

        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public CouponDTO updateCoupon(Long couponId, Coupon coupon) {
        couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "couponId", couponId));

        coupon.setId(couponId);

        couponRepository.save(coupon);

        return modelMapper.map(coupon, CouponDTO.class);
    }

    @Override
    public CouponDTO activateCoupon(Long couponId) {
        Coupon savedCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "couponId", couponId));

        savedCoupon.setIsActive(!savedCoupon.getIsActive());
        couponRepository.save(savedCoupon);

        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        Coupon savedCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "couponId", couponId));

        couponRepository.delete(savedCoupon);
    }

}
