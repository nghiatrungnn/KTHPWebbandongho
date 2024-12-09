package com.example.KTHP.webdongho.services;


import com.example.KTHP.webdongho.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Product, Integer> {


}

