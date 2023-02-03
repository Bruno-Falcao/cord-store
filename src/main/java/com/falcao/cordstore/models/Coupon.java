package com.falcao.cordstore.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Coupon {

    @Id
    private String id;

    private Boolean activated = false;

    private String startDate;
    private String expirationTime;

    private Integer discountPercentage;

    public Coupon() {
        if (this.activated) {
            this.startDate = LocalDateTime.now().toString();
        }
    }



    //TODO
    /**
     * This method validates if the coupon is expired
     *
     * @param coupon
     * @return
     */
    public Boolean isValid(Coupon coupon) {
        return false;
    }
}
