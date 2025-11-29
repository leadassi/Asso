package com.ecommerce.paiement.web.service;


import com.ecommerce.paiement.web.dto.ContenanceDto;
import com.ecommerce.paiement.web.dto.OrderDto;
import com.ecommerce.paiement.web.dto.PanierDto;
import com.ecommerce.paiement.web.exceptions.ResourceNotFoundException;
import com.ecommerce.paiement.web.model.Payment;
import com.ecommerce.paiement.web.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OrderService orderService;
    @Value("${external.produitservice.url}")
    private String productservice;
    @Value("${external.livraisonservice.url}")
    private String deliveryservice;
    //@Retry(name = "paymentService", fallbackMethod = "fallbackCreatePayment")
    //@TimeLimiter(name = "paymentService")
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    //@Retry(name = "paymentService")
    //@TimeLimiter(name = "paymentService")
    public Optional<Payment> searchPayment(int id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> findByOrderId(int order_id){
        List<Payment> payments = paymentRepository.findByOrderId(order_id);
        if (payments.isEmpty()) {
            throw new ResourceNotFoundException("Aucun paiement trouvée pour la commande avec ID: " + order_id);
        }
        return payments;
    }

    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    //Retry(name = "paymentService")
    //@TimeLimiter(name = "paymentService")
    public Payment updatePayment(int id, Payment new_payment) {
        Payment existing_payment = paymentRepository.findById(id).orElse(null);
        if (existing_payment != null) {
            existing_payment.setOrderId(new_payment.getOrderId());
            existing_payment.setCustomer_id(new_payment.getCustomer_id());
            existing_payment.setPayment_method(new_payment.getPayment_method());
            existing_payment.setPayment_state(new_payment.getPayment_state());
            return paymentRepository.save(existing_payment);
        }
        return null;
    }

    //@Retry(name = "paymentService")
    //@TimeLimiter(name = "paymentService")
    public String sendPaymentRequest (Payment payment) {
        String url_delivery_service = deliveryservice;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(payment);
            System.out.println("Serialized Payment JSON: " + jsonRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url_delivery_service, request, String.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error serializing payment request";
        }
    }

    public List<String> reduceQuantity (int orderId) {

        List<String> responses = new ArrayList<>();
        OrderDto orderDto = orderService.getData(orderId);
        int idPanier = orderDto.getIdPanier();
        PanierDto panierDto = orderService.getPanierData(idPanier);
        List<ContenanceDto> contenanceDto = panierDto.getContenances();

        for (ContenanceDto content : contenanceDto) {
            int productId = content.getIdProduit();
            int quantity = content.getQuantite();

            String url = productservice + productId + "?quantity=" + quantity;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                responses.add("Réponse pour le produit ID " + productId + ": " + response.getBody());
            } catch (Exception e) {
                responses.add("Erreur pour le produit ID " + productId + ": " + e.getMessage());
            }
        }
        return responses;
    }

    public boolean deletePayment(int id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Payment fallbackCreatePayment(Payment payment, Throwable throwable) {
        System.out.println("Erreur de paiement : " + throwable.getMessage());
        return new Payment();
    }

}
