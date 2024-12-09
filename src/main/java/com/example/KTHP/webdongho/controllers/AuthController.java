package com.example.KTHP.webdongho.controllers;

import com.example.KTHP.webdongho.models.User;
import com.example.KTHP.webdongho.services.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String register() {
        return "auth/register";
    }

    @Transactional
    @PostMapping("/auth/register")
    public String registerUser(User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "auth/register";
        }

        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/auth/login";
    }

    @PostMapping("/auth/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
            // Đăng nhập thành công
            session.setAttribute("user", existingUser); // Lưu thông tin người dùng vào phiên
            return "redirect:/products/index"; // Chuyển hướng đến trang sản phẩm
        }

        model.addAttribute("error", "Thông tin đăng nhập không hợp lệ");
        return "auth/login"; // Quay lại trang đăng nhập nếu thông tin không hợp lệ
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa session
        return "redirect:/auth/login"; // Chuyển hướng về trang đăng nhập
    }

    @GetMapping("/auth/products") // Thay đổi đường dẫn ở đây
    public String viewProducts(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        // Thêm mã để lấy danh sách sản phẩm và thêm vào mô hình
        return "products/index"; // Trả về trang sản phẩm
    }
}
