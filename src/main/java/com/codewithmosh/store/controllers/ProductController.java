package com.codewithmosh.store.controllers;


import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductWrapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@Tag(name="Products")
public class ProductController {
    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ProductWrapper productWrapper;

    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(name = "categoryId", required = false, defaultValue = "") Byte categoryId) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }
        return products.stream().map(product -> productWrapper.toDto(product)).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProducts(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productWrapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);

        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        var product = productWrapper.toEntity(productDto);

        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        return ResponseEntity.ok(productWrapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productWrapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

}
