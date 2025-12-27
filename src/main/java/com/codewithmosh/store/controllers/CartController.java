package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Exceptions.CartNotFoundException;
import com.codewithmosh.store.Exceptions.ProductNotFoundException;
import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.mappers.CartMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
@Data
@Tag(name = "Carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> CreateCart() {
        var cartDto = cartService.createCart();
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }


    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest req) {
        var cartDto = cartService.addToCart(cartId,req.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCartItem(@PathVariable UUID cartId) {
        return cartService.getCartItem(cartId);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItem(@PathVariable UUID cartId, @Valid @PathVariable Long productId, @RequestBody UpdateCartItemRequest updateCartItemRequest) {
        return cartService.updateCartItem(cartId,productId,updateCartItemRequest.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.deleteCartItem(cartId,productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> handleCartNotFoundException(CartNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errors", "Cart not Found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public  ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errors", "Product not Found"));
    }
}
