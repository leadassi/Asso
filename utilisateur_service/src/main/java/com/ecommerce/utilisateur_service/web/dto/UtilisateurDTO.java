package com.ecommerce.utilisateur_service.web.dto;

import com.ecommerce.utilisateur_service.model.Utilisateur;
import jakarta.validation.constraints.NotNull;

public class UtilisateurDTO {

    private Integer id;
    private String nom;
    private String prenom;
    private Utilisateur.Sexe sexe;
    private String email;
    private int numerotelephone;
    private String pays;
    private String ville;
    private String quartier;
    private int BP;

    public UtilisateurDTO() {}

    // Constructeur pour transformer Utilisateur en UtilisateurDTO
    public UtilisateurDTO(Utilisateur utilisateur) {
        this.id = utilisateur.getId();
        this.email = utilisateur.getEmail();
        this.nom = utilisateur.getNom();
        this.prenom = utilisateur.getPrenom();
        this.numerotelephone=utilisateur.getNumerotelephone();
        this.pays=utilisateur.getPays();
        this.quartier=utilisateur.getQuartier();
        this.sexe=utilisateur.getSexe();
        this.ville=utilisateur.getVille();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public int getNumerotelephone() {
        return numerotelephone;
    }

    public void setNumerotelephone(int numerotelephone) {
        this.numerotelephone = numerotelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Utilisateur.Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Utilisateur.Sexe sexe) {
        this.sexe = sexe;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
