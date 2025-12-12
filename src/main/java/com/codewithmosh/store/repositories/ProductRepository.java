package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {

    List<Product> findByName(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByPrice(double price);

    List<Product> findByNameOrderByPriceDesc(String name);
}
