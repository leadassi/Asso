package com.ecommerce.paiement.web.controller;


import com.ecommerce.paiement.web.dto.ContenanceDto;
import com.ecommerce.paiement.web.dto.PostOrderIdRequest;
import com.ecommerce.paiement.web.exceptions.ResourceNotFoundException;
import com.ecommerce.paiement.web.model.Payment;
import com.ecommerce.paiement.web.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    // Enpoint to research a payment by id
    @Operation(summary = "Rechercher un paiement avec son identifiant.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPayment(@PathVariable int id) {
        Optional<Payment> payment = paymentService.searchPayment(id);
        if (payment.isEmpty()) throw new ResourceNotFoundException("Le paiement avec l'id "+id+" est INTROUVABLE.");
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Rechercher un paiement avec l'identifiant de la commande.")
    @GetMapping("findbyorderid/{order_id}")
    public ResponseEntity<List<Payment>> getPaymentByOrderId(@PathVariable int order_id){
        List<Payment> payments = paymentService.findByOrderId(order_id);
        if (payments.isEmpty()) {
            throw new ResourceNotFoundException("Aucun paiement trouvée pour la commande avec ID: " + order_id);
        }
        return ResponseEntity.ok(payments);
    }

    // Endpoint to update a payment
    @Operation(summary = "Mettre à jour des informations d'un paiment.")
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int id, @RequestBody Payment updated_payment) {
        Payment payment = paymentService.updatePayment(id, updated_payment);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint to get all payments
    @Operation(summary = "Récupérer les informations sur tous les paiements initiés.")
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayment() {
        List<Payment> payment = paymentService.getAllPayment();
        return ResponseEntity.ok(payment);
    }

    // Endpoint to save a payment
    @Operation(summary = "Enregistrer les informations d'un paiment.")
    @PostMapping("/savepayment")
    public ResponseEntity<Payment> savePayment(@RequestBody Payment new_payment) {
        Payment payment = paymentService.createPayment(new_payment);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @Operation(summary = "Envoyer les informations d'un paiment.", description = "Envoie les informations d'un paiement ayant le statut ACCEPTED au service de livraison " +
            "pour enclencher le processus de livraison de la commande.")
    @PostMapping("/sendpayment")
    public ResponseEntity<String> sendPayment(@RequestBody Payment payment) {
        String result = paymentService.sendPaymentRequest(payment);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Décrémenter les quantités des produits.", description = "Décrémente la quantité des produits commandés dans le service de gestion des produits" +
            " après paiement en fonction de l'id de la commande.")
    @PostMapping("/orders/contenancepanier")
    public ResponseEntity<List<String>> sendContenance (@RequestBody PostOrderIdRequest payload){
        int orderId = payload.getOrderId();
        List<String> result = paymentService.reduceQuantity(orderId);
        return ResponseEntity.ok(result);
    }

}
