package com.ecommerce.paiement.web.model;


import com.ecommerce.paiement.web.enums.Method;
import com.ecommerce.paiement.web.enums.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "order_id")
    @JsonProperty("orderId")
    private int orderId;
    private int customer_id;
    private int amount;
    @Enumerated(EnumType.STRING)
    private Method payment_method;
    @Enumerated(EnumType.STRING)
    private State payment_state;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate payment_date;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalTime payment_time;

    public Payment(){

    }

    public Payment(int id, int orderId, int customer_id, Method payment_method, State payment_state){
        this.id = id;
        this.orderId = orderId;
        this.customer_id = customer_id;
        this.payment_method = payment_method;
        this.payment_state = payment_state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Method getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Method payment_method) {
        this.payment_method = payment_method;
    }

    public State getPayment_state() {
        return payment_state;
    }

    public void setPayment_state(State payment_state) {
        this.payment_state = payment_state;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getPayment_date() {
        return payment_date;
    }

    public LocalTime getPayment_time() {
        return payment_time;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customer_id=" + customer_id +
                ", amount='" + amount + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", payment_state='" + payment_state + '\'' +
                ", payment_time='" + payment_time + '\'' +
                ", payment_date='" + payment_date + '\'' +
                '}';
    }
}
