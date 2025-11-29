package com.monprojet.commande.models;

import com.monprojet.commande.enums.TypeCategory;
import com.monprojet.commande.enums.TypeSousCategory;

public class Produit {

    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private TypeCategory category;
    private String imageUrl;
    private TypeSousCategory subCategory;

    public Produit() {
    }

    public Produit(int id, String name, String description, float price, int quantity, TypeCategory category, String imageUrl, TypeSousCategory subCategory) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.subCategory = subCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public TypeCategory getCategory() {
        return category;
    }

    public void setCategory(TypeCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TypeSousCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(TypeSousCategory subCategory) {
        this.subCategory = subCategory;
    }
}


