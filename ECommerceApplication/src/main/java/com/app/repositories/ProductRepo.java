package com.app.repositories;

import com.app.entites.Brand;
import com.app.entites.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entites.Category;
import com.app.entites.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	Page<Product> findByProductNameLike(String keyword, Pageable pageDetails);
    Page<Product> findByCategory(Category category, Pageable pageDetails);
    Page<Product> findByBrand(Brand brand, Pageable pageDetails);
    Page<Product> findByCoupon(Coupon coupon, Pageable pageDetails);

    @Query("SELECT p FROM Product p WHERE p.price >= :lowerPrice AND p.price <= :upperPrice")
    Page<Product> findByPriceRange(
            @Param("lowerPrice") Double lowerPrice,
            @Param("upperPrice") Double upperPrice,
            Pageable pageable
    );
}
