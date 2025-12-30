package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCartItemRequest {
    @NotNull
    private Integer quantity;
}
