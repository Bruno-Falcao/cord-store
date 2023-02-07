package com.falcao.cordstore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Document(collection = "coupons")
public class Coupon {

    @Id
    private String id;
    @JsonProperty("coupon_name")
    @NotBlank(message = "Coupon name can not be blank")
    private String couponName;
    @NotBlank(message = "Activated should be defined as 'true' or 'false'")
    private Boolean activated;
    @JsonProperty("start_date")
    @NotBlank(message = "Start date can not be blank")
    private String startDate;
    @JsonProperty("expiration_date")
    private String expirationDate;
    @JsonProperty("min_value")
    private BigDecimal minValue;
    @JsonProperty("discount_percentage")
    private Integer discountPercentage;
}
