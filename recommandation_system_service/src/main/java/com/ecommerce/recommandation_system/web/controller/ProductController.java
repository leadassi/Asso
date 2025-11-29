package com.ecommerce.recommandation_system.web.controller;


import com.ecommerce.recommandation_system.web.dto.ProductDto;
import com.ecommerce.recommandation_system.web.service.RecommandationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    RecommandationService recommandationService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id){
        ProductDto productDto = recommandationService.getProductData(id);
        return ResponseEntity.ok(productDto);
    }
}
