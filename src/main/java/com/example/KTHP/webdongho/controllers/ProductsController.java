package com.example.KTHP.webdongho.controllers;

import com.example.KTHP.webdongho.models.Product;
import com.example.KTHP.webdongho.models.User;
import com.example.KTHP.webdongho.services.CartService;
import com.example.KTHP.webdongho.services.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsRepository repo;

    @Autowired
    private CartService cartService;

    @GetMapping({"", "/"})
    public String showProductList(Model model, HttpSession session) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);

        User user = (User) session.getAttribute("user"); // Lấy thông tin người dùng từ phiên
        model.addAttribute("user", user); // Thêm thông tin người dùng vào model

        return "products/index"; // Trả về trang sản phẩm
    }

    @GetMapping("/index")
    public String showProducts(Model model, HttpSession session) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);

        User user = (User) session.getAttribute("user"); // Lấy thông tin người dùng từ phiên
        model.addAttribute("user", user); // Thêm thông tin người dùng vào model

        return "products/product"; // Trả về trang sản phẩm
    }

    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable int id, Model model) {
        Product product = repo.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "products/detail";
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable int id, @RequestParam int quantity) {
        Product product = repo.findById(id).orElse(null);
        if (product != null) {
            cartService.addToCart(product, quantity);
        }
        return "redirect:/products/cart"; // Chuyển hướng đến giỏ hàng
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCart());
        return "products/cart";
    }
}
