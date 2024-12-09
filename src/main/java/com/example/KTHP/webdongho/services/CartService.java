package com.example.KTHP.webdongho.services;

import com.example.KTHP.webdongho.models.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    private Map<Integer, Integer> cart = new HashMap<>(); // productId -> quantity

    public void addToCart(Product product, int quantity) {
        if (cart.containsKey(product.getId())) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            cart.put(product.getId(), cart.get(product.getId()) + quantity);
        } else {
            // Nếu sản phẩm chưa có, thêm mới
            cart.put(product.getId(), quantity);
        }
    }

    public Map<Integer, Integer> getCart() {
        return cart;
    }
}
