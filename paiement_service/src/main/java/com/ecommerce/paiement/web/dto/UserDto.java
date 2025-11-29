package com.ecommerce.paiement.web.dto;


public class UserDto {

    public enum Sexe{
        masculin,
        feminin
    }
    private Integer id;
    private String nom;
    private String prenom;
    private Sexe sexe;
    private String email;
    private String mot_passe;
    private int numerotelephone;
    private String pays;
    private String ville;
    private String quartier;


    public UserDto() {
    }

    public UserDto(String nom, String prenom, Sexe sexe, String email, String mot_passe, int numerotelephone, String pays, String ville, String quartier){

        this.nom=nom;
        this.prenom=prenom;
        this.sexe=sexe;
        this.email=email;
        this.mot_passe=mot_passe;
        this.numerotelephone=numerotelephone;
        this.pays=pays;
        this.ville=ville;
        this.quartier=quartier;

    }

    //Getters

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {return prenom;}

    public Sexe getSexe() {
        return sexe;
    }

    public String getEmail() {
        return email;
    }

    public int getNumerotelephone() {
        return numerotelephone;
    }

    public String getPays() {return pays;}

    public String getVille() {return ville;}

    public String getQuartier() {return quartier;}

    public String getMot_passe() {return mot_passe;}

    //public String getEtat() {return etat;}

    //public String getToken() {return token;}

    //Setters

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {this.prenom = prenom;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMot_passe(String mot_passe) {
        this.mot_passe = mot_passe;
    }

    public void setNumerotelephone(int numerotelephone) {
        this.numerotelephone = numerotelephone;
    }

    public void setPays(String pays) {this.pays = pays;}

    public void setVille(String ville) {this.ville = ville;}

    public void setQuartier(String quartier) {this.quartier = quartier;}

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString(){

        return "Utilisateur{"+
                "id="+id+
                ", nom="+nom+
                ", prenom="+prenom+
                ", email="+email+
                ", numerotelephone="+numerotelephone+
                ", pays="+pays+
                ", ville="+ville+
                ", quartier="+quartier+
                "}";
    }

    public void setId(int userId) {
        this.id = userId;
    }
}
