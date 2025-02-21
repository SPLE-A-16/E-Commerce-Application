package com.app.services;

import com.app.entites.Category;
import com.app.entites.Coupon;
import com.app.payloads.CategoryDTO;
import com.app.payloads.CategoryResponse;
import com.app.payloads.CouponDTO;

public interface CouponService {

	CouponDTO validateCoupon(String code);
	CouponDTO createCoupon(Coupon coupon);
	CouponDTO updateCoupon(Long id, Coupon coupon);
	CouponDTO activateCoupon(Long id);
	void deleteCoupon(Long id);
}
