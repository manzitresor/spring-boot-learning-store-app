package com.codewithmosh.store.mappers;


import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductWrapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);
}
