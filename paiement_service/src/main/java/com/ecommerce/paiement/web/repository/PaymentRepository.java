package com.ecommerce.paiement.web.repository;

import com.ecommerce.paiement.web.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByOrderId(int order_id);
}
