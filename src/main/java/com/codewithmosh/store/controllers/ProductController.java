package com.codewithmosh.store.controllers;


import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductWrapper;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;
    private ProductWrapper productWrapper;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(name = "categoryId", required = false, defaultValue = "") Byte categoryId){
        List<Product> products;
        if(categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }
        return products.stream().map(product -> productWrapper.toDto(product)).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProducts(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productWrapper.toDto(product));
    }

}
