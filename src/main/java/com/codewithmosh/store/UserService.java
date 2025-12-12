package com.codewithmosh.store;

import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.AddressRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Data
@Service
public class UserService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ProductRepository productRepository;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
    }


    public void fetchAllUsers() {
        var address = addressRepository.findById(1L).orElseThrow();
        System.out.println(address);
    }

    public void persistRelated(){
        var user = User.builder()
                        .name("John")
                        .password("1234")
                        .email("john@gmail.com")
                        .build();
        var address = Address.builder()
                .zip("1234")
                .city("Kigali")
                .street("123 Main St")
                .state("arizona")
                .build();

        user.addAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void deleteRelated(){
       var user = userRepository.findById(3L).orElseThrow();
       var address = user.getAddresses().getFirst();
       user.removeAddress(address);
    }

    @Transactional
    public void manageProduct(){
       var user = userRepository.findById(3L).orElseThrow();
       var products = productRepository.findAll();

       products.forEach(user::addFavoriteProduct);
    }

    @Transactional
    public void updateProductPrices(){
        productRepository.updatePriceByCategory(BigDecimal.valueOf(10), (byte)10);
    }
}
