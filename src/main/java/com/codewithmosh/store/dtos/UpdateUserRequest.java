package com.codewithmosh.store.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class UpdateUserRequest {
    private String name;
    private String email;
}
