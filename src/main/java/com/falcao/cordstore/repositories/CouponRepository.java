package com.falcao.cordstore.repositories;

import com.falcao.cordstore.models.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CouponRepository extends MongoRepository<Coupon, String> {

    @Query("{'activated': true}")
    List<Coupon> findAllByVisibility();

    @Query(value = "{'couponName': {$regex: ?0, $options: 'i'}, 'activated': true}")
    public List<Coupon> findActiveCouponByName(String name);
}
