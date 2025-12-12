package com.codewithmosh.store;

import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.AddressRepository;
import com.codewithmosh.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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

}
