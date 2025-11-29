package com.ecommerce.recommandation_system.web.controller;

import com.ecommerce.recommandation_system.web.dto.ProductDto;
import com.ecommerce.recommandation_system.web.model.Recommandation;
import com.ecommerce.recommandation_system.web.service.RecommandationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommandations")
@Validated
@CrossOrigin("*")
public class RecommandationController {
    @Autowired
    RecommandationService recommandationService;

    @Operation(summary = "Retourne toutes les recommandations", description = "Retourne les 15 produits ayant obtenus la meilleure moyenne de votes.")
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> sendRecommandation(){
        List<ProductDto> recommandation = recommandationService.processRecommandation();
        return ResponseEntity.ok(recommandation);
    }

    @Operation(summary = "Sauvegarder une note", description = "Enregistre en base de données la note d'un produit pour un tilisateur donné.")
    @PostMapping("/saverecommandation")
    public ResponseEntity<Recommandation> saveRecmmandation(@RequestBody @Valid Recommandation new_recommandation){
        Recommandation recommandation = recommandationService.createRecommandation(new_recommandation);
        return new ResponseEntity <>(recommandation, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtenir la note d'un produit", description = "Retourne la moyenne des notes d'un produit en entrant son identifiant.")
    @GetMapping("/ratings/{productId}")
    public float sendProductRate(@PathVariable int productId){
        float rate = recommandationService.getProductRate(productId);
        return rate;
    }
}
