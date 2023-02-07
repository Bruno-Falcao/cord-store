package com.falcao.cordstore.models;

import com.falcao.cordstore.models.enums.Catogories;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Document(value = "products")
public class Product {

    @Id
    private String id;
    @JsonProperty("product_name")
    @NotBlank(message = "product_name should not be blank")
    private String productName;
    @NotBlank(message = "Brand should not be blank")
    private String brand;
    private String description;
    @NotBlank(message = "You should attribute a price to the product")
    private BigDecimal value;
    private Catogories category;
    private Boolean visibility;

    private Integer quantity;
}

