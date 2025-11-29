package com.ecommerce.recommandation_system.web.service;

import com.ecommerce.recommandation_system.web.dto.ProductDto;
import com.ecommerce.recommandation_system.web.exceptions.ResourceNotFoundException;
import com.ecommerce.recommandation_system.web.exceptions.ServiceException;
import com.ecommerce.recommandation_system.web.model.Recommandation;
import com.ecommerce.recommandation_system.web.repository.RecommandationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommandationService {

    private final RecommandationRepository recommandationRepository;
    private final RestTemplate restTemplate;
    @Value("${external.produitservice.url}")
    private String produitservice;

    public RecommandationService(RestTemplate restTemplate, RecommandationRepository recommandationRepository) {
        this.restTemplate = restTemplate;
        this.recommandationRepository = recommandationRepository;
    }


    /*public List<Recommandation> getAllRecommandation(){
        return recommandationRepository.findAll();
    }*/

    public List<Recommandation> findByProductId (int productId){
        List<Recommandation> recommendations = recommandationRepository.findByProductId(productId);

        if (recommendations.isEmpty()) {
            throw new ResourceNotFoundException("Aucune recommandation trouvée pour le produit avec ID: " + productId);
        }

        return recommendations;
    }

    public Recommandation createRecommandation(Recommandation recommandation){
        return recommandationRepository.save(recommandation);
    }

    public List<ProductDto> processRecommandation() {
        List<Recommandation> ratings_data = recommandationRepository.findAll();

        // Grouper par ProductId et sommer les évaluations (ratings)
        Map<String, Double> productAverages = ratings_data.stream()
                .collect(Collectors.groupingBy(
                        recommandation -> String.valueOf(recommandation.getProductId()), // Conversion en String
                        Collectors.teeing(
                                Collectors.summingInt(Recommandation::getRating),   // Somme des évaluations (ratings)
                                Collectors.counting(),                              // Compter les évaluations
                                (sum, count) -> (double) sum / count                // Calculer la moyenne
                        )
                ));

        // Tri décroissant par la moyenne des évaluations
        List<Map.Entry<String, Double>> sortedProducts = productAverages.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())) // Tri décroissant
                .toList();

        List<ProductDto> recommendedProducts = new ArrayList<>();

        // Limiter à 15 produits et les ajouter à la liste
        sortedProducts.stream().limit(7).forEach(entry -> {
            String productId = entry.getKey();
            // Obtenir les données du produit via le service de gestion des produits
            int id = Integer.parseInt(productId);
            ProductDto recommendProduct = getProductData(id);
            recommendedProducts.add(recommendProduct);
        });

        return recommendedProducts;


    }

    public ProductDto getProductData(int productId){
        String url = produitservice + productId;
        try{
            ResponseEntity<ProductDto> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            ProductDto.class
                    );
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Produit avec l'id "+productId+" non trouvée"+e);
        }catch(Exception e){
            throw new ServiceException("Service de gestion des produits INDISPONIBLE !"+e);
        }
    }

    public float getProductRate(int productId) {
        // Récupérer les recommandations liées au produit
        List<Recommandation> recommandations = recommandationRepository.findByProductId(productId);

        // Vérifier s'il y a des recommandations pour éviter les divisions par zéro
        if (recommandations.isEmpty()) {
            return (float) 0; // Retourne 0 si le produit n'a aucune évaluation
        }

        // Calculer la somme totale des ratings
        int totalRatings = recommandations.stream()
                .mapToInt(Recommandation::getRating) // Récupère la note (rating) de chaque recommandation
                .sum();

        // Calculer le nombre total de recommandations
        int count = recommandations.size();

        // Calculer la moyenne des ratings
        return (float) totalRatings / count;
    }

}
