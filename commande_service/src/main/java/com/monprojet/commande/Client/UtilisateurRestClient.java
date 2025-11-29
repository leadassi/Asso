package com.monprojet.commande.Client;

import com.monprojet.commande.enums.Sexe;
import com.monprojet.commande.models.Utilisateur;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

@Service
public class UtilisateurRestClient {

    private final RestTemplate restTemplate;

    @Value("${utilisateur.service.url}")
    private String findUserUrl;

    public UtilisateurRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*@CircuitBreaker(name = "Utilisateur_service", fallbackMethod = "fallbackGetUserById")
    public Utilisateur findUserById(int userId) {
        String url = findUserUrl + "/AffichageUtilisateur/" + userId;
        return restTemplate.getForObject(url, Utilisateur.class);
    }*/

    @CircuitBreaker(name = "Utilisateur_service", fallbackMethod = "fallbackGetUserById")
    public Utilisateur findUserById(int userId) {
        String url = "http://192.168.181.193:9091/Utilisateurs/" + userId;
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("user", "password123");

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<Utilisateur> response = restTemplate.exchange(url, HttpMethod.GET, request, Utilisateur.class);
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Utilisateur non trouvé");
        }catch(Exception e){
            throw new RuntimeException("Erreur lors de l'appel du service utilisateur");
        }
    }



    @CircuitBreaker(name = "produitService", fallbackMethod = "fallbackGetAllUser")
    public List<Utilisateur> findAllUser() {
        String url = findUserUrl + "/findAllUser";
        Utilisateur[] users = restTemplate.getForObject(url, Utilisateur[].class);
        assert users != null;
        return Arrays.asList(users);
    }

    public Utilisateur fallbackGetUserById(int userId, Exception exception) {
        Utilisateur utilisateurFallBack = new Utilisateur();
        utilisateurFallBack.setId(userId) ;
        utilisateurFallBack.setNom("Not available");
        utilisateurFallBack.setPrenom("Not available");
        utilisateurFallBack.setEmail("Not available");
        utilisateurFallBack.setSexe(Sexe.feminin);
        utilisateurFallBack.setNumerotelephone(000000000);
        utilisateurFallBack.setPays("Not available");
        utilisateurFallBack.setVille("Not available");
        utilisateurFallBack.setQuartier("Not available");
        utilisateurFallBack.setBp("Not available");
        utilisateurFallBack.setMot_passe("Not available");
        return utilisateurFallBack;
    }

    public List<Utilisateur> fallbackGetAllUser(Exception exception) {
        return Arrays.asList();
    }
}



