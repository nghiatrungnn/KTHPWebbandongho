package com.example.KTHP.webdongho.controllers;

import com.example.KTHP.webdongho.models.Product;
import com.example.KTHP.webdongho.services.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    @Autowired
    private ProductsRepository productRepository;

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin/products/list"; // Tạo file list.html trong thư mục admin/products
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/products/add"; // Tạo file add.html trong thư mục admin/products
    }

    // Xử lý thêm sản phẩm
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        product.setCreatedAt(new Date()); // Thiết lập ngày tạo
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            // Xử lý khi không tìm thấy sản phẩm
            return "redirect:/admin/products"; // Hoặc hiển thị thông báo lỗi
        }
        model.addAttribute("product", product);
        return "admin/products/edit"; // Tạo file edit.html trong thư mục admin/products
    }

    // Xử lý sửa sản phẩm
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable int id, @ModelAttribute Product product) {
        product.setId(id); // Đảm bảo rằng id được thiết lập
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Xử lý xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}
