package com.monprojet.commande.controller;

import com.monprojet.commande.DTO.PanierDto;
import com.monprojet.commande.models.Contenance;
import com.monprojet.commande.models.ContenanceDTO;
import com.monprojet.commande.models.Panier;
import com.monprojet.commande.repository.PanierRepository;
import com.monprojet.commande.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/commande/panier")
@CrossOrigin("*")
public class PanierController {

    @Autowired
    private PanierService panierService;
    @Autowired
    private PanierRepository panierRepository;
    // Endpoint pour récupérer tous les paniers
    @GetMapping
    public ResponseEntity<List<Panier>> getAllPaniers() {
        List<Panier> paniers = panierRepository.findAll();
        return new ResponseEntity<>(paniers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PanierDto> getPanierById(@PathVariable int id) {
        Optional<Panier> panier = panierRepository.findById(id);
        if (panier.isPresent()) {
            PanierDto dto = mapToPanierDTO(panier.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    private PanierDto mapToPanierDTO(Panier panier) {
        PanierDto dto = new PanierDto();
        dto.setIdPanier(panier.getIdPanier());
        dto.setIdUtilisateur(panier.getIdUtilisateur());
        dto.setPrixTotal(panier.getPrixTotal());
        dto.setContenances(panier.getContenances().stream().map(this::mapToContenanceDTO).collect(Collectors.toList()));
        return dto;
    }

    private ContenanceDTO mapToContenanceDTO(Contenance contenance) {
        ContenanceDTO dto = new ContenanceDTO();
        dto.setIdProduit(contenance.getIdProduit());
        dto.setQuantite(contenance.getQuantite());
        return dto;
    }



    @PostMapping("/validerPanier")
    public ResponseEntity<Integer> createOrUpdatePanier(@RequestBody Panier panier) {
        // Log pour debug
        System.out.println("Données reçues pour le panier : " + panier);

        if (panier.getIdUtilisateur() == 0 || panier.getContenances() == null || panier.getContenances().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            for (Contenance contenance : panier.getContenances()) {
                contenance.setPanier(panier);
            }

            Panier savedPanier = panierRepository.save(panier);
            return new ResponseEntity<>(savedPanier.getIdPanier(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    // Endpoint pour calculer et récupérer le prix total d'un panier spécifique
    @GetMapping("/{id}/total")
    public ResponseEntity<Double> getPanierTotal(@PathVariable int id) {
        Optional<Panier> panier = panierRepository.findById(id);
        if (panier.isPresent()) {
            double total = panierService.calculerPrixTotalFacture(panier.get().getIdPanier());
            return new ResponseEntity<>(total, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
