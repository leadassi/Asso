package com.ecommerce.paiement.web.service;


import com.ecommerce.paiement.web.dto.UserDto;
import com.ecommerce.paiement.web.exceptions.ServiceException;
import com.ecommerce.paiement.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class UserService {

    private final RestTemplate restTemplate;
    @Value("${external.utilisateurservice.url}")
    private String userservice;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getData(int user_id) {
        String url = userservice + user_id;
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("user", "password123");

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.GET, request, UserDto.class);
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Utilisateur avec l'id "+user_id+" non trouvé"+e);
        }catch(Exception e){
            throw new ServiceException("Service de gestion des utilisateurs INDISPONIBLE !"+e);
        }
    }
}
