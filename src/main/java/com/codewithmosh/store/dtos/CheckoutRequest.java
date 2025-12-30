package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public class CheckoutRequest {
    @NotNull(message = "Cart Id is required")
    private UUID cartId;
}
