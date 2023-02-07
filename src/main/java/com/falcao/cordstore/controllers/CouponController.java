package com.falcao.cordstore.controllers;

import com.falcao.cordstore.models.Coupon;
import com.falcao.cordstore.services.CouponService;
import com.falcao.cordstore.utils.ResponseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/save_coupon")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> createCoupon(@RequestBody Coupon coupon) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(couponService.saveCoupon(coupon))));

        } catch (Exception ex) {
            return errorResponse("Error saving coupon", ex);
        }
    }

    @PutMapping("/update_coupon")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> updateCoupon(@RequestBody Coupon coupon) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(couponService.updateCoupon(coupon))));

        } catch (Exception ex) {
            return errorResponse("Error updating coupon", ex);
        }
    }

    @GetMapping("/find_active_coupons")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> findAllActivateCoupons() {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(couponService.findAllActiveCoupons())));

        } catch (Exception ex) {
            return errorResponse("Error finding coupons", ex);
        }
    }

    @GetMapping("/find_active_coupons_by_name")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Object> findActiveCouponsByName(@RequestParam("name")String name) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ResponseAPI.getInstance(Collections
                            .singletonList(couponService.findActiveCouponsByName(name))));

        } catch (Exception ex) {
            return errorResponse("Error finding coupons", ex);
        }
    }

    private ResponseEntity<Object> errorResponse(String errorMsg, Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseAPI.getInstance(String.format(errorMsg, ex.getMessage()),
                        Arrays.stream(ex.getSuppressed()).map(Throwable::getMessage)
                                .toArray(String[]::new)));
    }
}
