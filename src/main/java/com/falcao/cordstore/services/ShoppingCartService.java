package com.falcao.cordstore.services;

import com.falcao.cordstore.models.ShoppingCart;
import com.falcao.cordstore.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private Map<String, ShoppingCart> shoppingCartMap;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
}
