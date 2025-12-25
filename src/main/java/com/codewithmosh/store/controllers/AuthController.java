package com.codewithmosh.store.controllers;


import com.codewithmosh.store.dtos.LoginDto;
import com.codewithmosh.store.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadRequest() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
