package com.falcao.cordstore.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ShoppingCart {
    @Id
    private String id;

}
