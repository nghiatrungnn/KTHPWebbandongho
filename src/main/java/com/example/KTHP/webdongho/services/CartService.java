package com.example.KTHP.webdongho.services;

import com.example.KTHP.webdongho.models.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    private Map<Product, Integer> cart = new HashMap<>();

    public void addToCart(Product product, int quantity) {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}
