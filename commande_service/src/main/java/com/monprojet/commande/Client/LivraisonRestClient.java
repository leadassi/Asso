package com.monprojet.commande.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LivraisonRestClient {
/*
    private final RestTemplate restTemplate;

    // URL de base du microservice de livraison (configurable via application.properties)
    @Value("${service.livraison.url}")
    private String livraisonServiceUrl;

    public LivraisonRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }*/

    /**
     * Récupère le montant de la livraison en fonction des critères nécessaires.
     *
     * @param codePostal Code postal de la livraison (par exemple).
     * @param poids Total du poids des articles (si pertinent).
     * @return Le montant de la livraison.
     */
   /* public double getMontantLivraison(String codePostal, double poids) {
        String url = livraisonServiceUrl + "/livraison/montant?codePostal=" + codePostal + "&poids=" + poids;

        // Effectue une requête GET vers l'API de livraison
        try {
            return restTemplate.getForObject(url, Double.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du montant de la livraison : " + e.getMessage(), e);
        }
    }*/
}
