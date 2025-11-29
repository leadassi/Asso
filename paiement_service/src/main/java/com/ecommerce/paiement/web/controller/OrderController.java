package com.ecommerce.paiement.web.controller;


import com.ecommerce.paiement.web.dto.ContenanceDto;
import com.ecommerce.paiement.web.dto.OrderDto;
import com.ecommerce.paiement.web.dto.UserDto;
import com.ecommerce.paiement.web.service.OrderService;
import com.ecommerce.paiement.web.service.PaymentService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> sendOrder (@PathVariable int id){
        OrderDto orderDto = orderService.getData(id);
        return ResponseEntity.ok(orderDto);
    }
}
