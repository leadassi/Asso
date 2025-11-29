package com.ecommerce.recommandation_system.web.dto;

import com.ecommerce.recommandation_system.web.enums.TypeCategory;
import com.ecommerce.recommandation_system.web.enums.TypeSousCategorie;

public class ProductDto {
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private TypeCategory category;
    private String imageUrl;
    private TypeSousCategorie subCategory;

    private int idFournisseur;

    public ProductDto() {

    }

    public ProductDto(int id, String name, String description, float price, int quantity, TypeCategory category, String imageUrl, TypeSousCategorie subCategory, int idFournisseur) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.subCategory = subCategory;
        this.idFournisseur = idFournisseur;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
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

    public float getPrice() {

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

    public TypeSousCategorie getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(TypeSousCategorie subCategory) {
        this.subCategory = subCategory;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", fournisseur='" + idFournisseur + '\''+

                '}';
    }
}
