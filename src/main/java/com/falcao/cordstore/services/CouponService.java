package com.falcao.cordstore.services;

import com.falcao.cordstore.execeptions.ExpirationDateException;
import com.falcao.cordstore.execeptions.NotFoundException;
import com.falcao.cordstore.models.Coupon;
import com.falcao.cordstore.repositories.CouponRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.falcao.cordstore.utils.Utils.getDateTimeFormatter;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * This method saves a new coupon based in some constraints
     * activated = true: Coupons necessarily need a valid expiration date
     * activated = false: Coupons necessarily need a valid start date and expiration date
     * @param coupon
     * @return
     * @throws Exception
     */
    public String saveCoupon(Coupon coupon) throws Exception {
        if (activatedCouponSaveContraints(coupon)) {
            couponRepository.save(coupon);
            return "Coupon saved successfully";

        } else if (deactivatedCouponSaveContraints(coupon)) {
            couponRepository.save(coupon);
            return "Coupon saved successfully";
        }
        throw new Exception("Error saving product");
    }

    /**
     * This method update an existing coupon, start date and expiration should be valid
     * @param coupon
     * @return
     * @throws Exception
     */
    public String updateCoupon(Coupon coupon) throws Exception {
        Optional<Coupon> couponById = findCouponById(coupon.getId());
        if (updateCouponValidation(coupon, couponById)) {
            fieldsUpdate(coupon, couponById);
            couponRepository.save(coupon);

            return "Coupon saved successfully";
        }
        throw new Exception("Error saving product");
    }

    /**
     * This method find a coupon using the id passed as parameter
     * @param id
     * @return
     */
    public Optional<Coupon> findCouponById(String id) {
        return Optional.ofNullable(couponRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Coupon not found")));
    }

    /**
     * This method finds all the activated coupons
     * @return
     */
    public List<Coupon> findAllActiveCoupons() {
        List<Coupon> coupons = couponRepository.findAllByVisibility();
        isEmpty(coupons);
        return coupons;
    }

    /**
     * This method finds all activated coupons based on passed name as parameter
     * @param name
     * @return
     */
    public List<Coupon> findActiveCouponsByName(String name) {
        List<Coupon> coupons = couponRepository.findActiveCouponByName(name);
        isEmpty(coupons);
        return coupons;
    }

    private boolean updateCouponValidation(Coupon coupon, Optional<Coupon> couponById) {
        return couponById.isPresent() && !(coupon.getStartDate().isEmpty() && coupon.getExpirationDate().isEmpty());
    }

    private boolean activatedCouponSaveContraints(Coupon coupon) {
        if (coupon != null
                && coupon.getId() == null
                && coupon.getDiscountPercentage() <= 50
                && coupon.getDiscountPercentage() > 1
                && coupon.getActivated().equals(true)
                && coupon.getExpirationDate() != null) {

            coupon.setStartDate(LocalDateTime.now().format(getDateTimeFormatter()));
            return couponDateValidation(coupon);
        }
        return false;
    }


    private boolean deactivatedCouponSaveContraints(Coupon coupon) {
        if (coupon != null
                && coupon.getId() == null
                && coupon.getDiscountPercentage() <= 50
                && coupon.getDiscountPercentage() > 1
                && coupon.getActivated().equals(false)
                && coupon.getStartDate() != null
                && coupon.getExpirationDate() != null) {

            return couponDateValidation(coupon);
        }
        return false;
    }

    /**
     * This method should compare the dates and return true if expiration time is ahead of the start date
     *
     * @return
     */
    private Boolean couponDateValidation(Coupon coupon) {
        LocalDateTime start = LocalDateTime.parse(coupon.getStartDate(), getDateTimeFormatter());
        LocalDateTime expiration = LocalDateTime.parse(coupon.getExpirationDate(), getDateTimeFormatter());

        if (start.isBefore(expiration)) {
            return true;
        }
        throw new ExpirationDateException("The start date is after the expiration date");
    }

    private void fieldsUpdate(Coupon coupon, Optional<Coupon> couponById) {
        if (couponDateValidation(coupon)) {
            couponById.get().setActivated(coupon.getActivated());
            couponById.get().setStartDate(coupon.getStartDate());
            couponById.get().setExpirationDate(coupon.getExpirationDate());
            couponById.get().setMinValue(coupon.getMinValue());
            couponById.get().setDiscountPercentage(coupon.getDiscountPercentage());
        }
    }

    private void isEmpty(List<Coupon> coupons) {
        if (coupons.isEmpty()) throw new NotFoundException("No products found");
    }
}
