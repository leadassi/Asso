package com.monprojet.commande.Client;

import com.monprojet.commande.models.Produit;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class ProduitRestClient {

    private final RestTemplate restTemplate;

    public  ProduitRestClient(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    private final String findProduitBaseUrl = "http://192.168.1.195:8080/produitService";

    @CircuitBreaker(name = "ProduitService", fallbackMethod = "fallbackGetProduitById" )
    public Produit findProduitById(int produitId) {
        String url = findProduitBaseUrl + "/findById/" + produitId;
        return restTemplate.getForObject(url, Produit.class);
    }

    @CircuitBreaker(name = "produitService", fallbackMethod = "fallbackGetAllProduit" )
    public List<Produit> findAllProduit() {
        String url = findProduitBaseUrl + "/findAllProduit";
        Produit[] produits = restTemplate.getForObject(url, Produit[].class);
        assert produits != null;
        return Arrays.asList(produits);
    }

    public Produit fallbackGetProduitById(int produitId, Exception exception) {
        Produit produitFallBack = new Produit();
        produitFallBack.setId(produitId);
        produitFallBack.setName("Not available");
        produitFallBack.setPrice(0);
        produitFallBack.setImageUrl("Not available");
        produitFallBack.setQuantity(0);
        produitFallBack.setDescription("None");

        return produitFallBack;
    }

    public List<Produit> fallbackGetAllProduit(Exception exception) {

        return Arrays.asList();
    }

}

