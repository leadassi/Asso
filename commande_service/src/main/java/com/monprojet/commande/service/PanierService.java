package com.monprojet.commande.service;

import com.monprojet.commande.Client.ProduitRestClient;
import com.monprojet.commande.models.Contenance;
import com.monprojet.commande.models.Panier;
import com.monprojet.commande.models.Produit;
import com.monprojet.commande.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PanierService {
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private ProduitRestClient produitRestClient;
    public double calculerPrixTotalFacture(int idPanier) {
        // Récupérer le panier par son ID
        Panier panier = panierRepository.findById(idPanier)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        double prixTotal = 0.0;

        // Récupérer les contenances associées au panier
        List<Contenance> contenances = panier.getContenances();

        for (Contenance contenance : contenances) {
            // Récupérer les détails du produit via ProduitRestClient
            Produit produit = produitRestClient.findProduitById(contenance.getIdProduit());

            // Calculer le prix
            prixTotal += produit.getPrice() * contenance.getQuantite();
        }

        return prixTotal;


    }

}