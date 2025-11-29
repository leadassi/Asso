package com.ecommerce.paiement.web.controller;

import com.ecommerce.paiement.web.dto.UserDto;
import com.ecommerce.paiement.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        UserDto user = userService.getData(id);
        return ResponseEntity.ok(user);
    }
}
