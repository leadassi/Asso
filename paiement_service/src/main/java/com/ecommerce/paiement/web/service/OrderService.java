package com.ecommerce.paiement.web.service;

import com.ecommerce.paiement.web.dto.ContenanceDto;
import com.ecommerce.paiement.web.dto.OrderDto;
import com.ecommerce.paiement.web.dto.PanierDto;
import com.ecommerce.paiement.web.exceptions.ServiceException;
import com.ecommerce.paiement.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class OrderService {

    private final RestTemplate restTemplate;
    @Value("${external.commandeservice1.url}")
    private String orderservice1;
    @Value("${external.commandeservice2.url}")
    private String orderservice2;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderDto getData(int order_id) {
        String url = orderservice1 + order_id;

        try{
            ResponseEntity<OrderDto> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            OrderDto.class
                    );

            return response.getBody();
        }catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Commande avec l'id "+order_id+" non trouvée"+e);
        }catch(Exception e){
            throw new ServiceException("Service de gestion des commandes INDISPONIBLE !"+e);
        }
    }

    public PanierDto getPanierData(int idPanier) {
        String url = orderservice2 + idPanier;
        try{
            ResponseEntity<PanierDto> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            null,
                            PanierDto.class
                    );
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Panier avec l'id "+idPanier+" non trouvée"+e);
        }catch(Exception e){
            throw new ServiceException("Service de gestion des commandes INDISPONIBLE !"+e);
        }
    }

}
