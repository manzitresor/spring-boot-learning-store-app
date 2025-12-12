package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {

    List<Product> findByName(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByPrice(double price);

    List<Product> findByNameOrderByPriceDesc(String name);

//    Using SQL
//    @Query(value = "select * from products p where p.price between :min and :max order by p.name", nativeQuery = true)
//    Using JPQL
    @Query("select p from Product p where p.price between :min and :max order by p.name")
    List<Product> findProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

//    For the updating operation we have to add modifying annotation
    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category.id = :categoryId")
    void updatePriceByCategory(BigDecimal newPrice, Byte categoryId);
}
