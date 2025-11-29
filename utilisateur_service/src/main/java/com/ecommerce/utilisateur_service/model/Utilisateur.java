package com.ecommerce.utilisateur_service.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "utilisateurs")
public class Utilisateur {


    public enum Sexe{
        masculin,     //0  représente le masculin
        feminin       //1 représente le féminin
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 3, max = 25, message = "nom invalide")
    @NotNull(message = "vous devez remplir un nom")
    @Column(name = "nom")
    private String nom;
    @Size(min = 3, max = 25, message = "prenom invalide")
    @NotNull(message = "vous devez remplir un prénom")
    @Column(name = "prenom")
    private String prenom;
    @NotNull(message = "vous devez choisir votre sexe")
    @Column(name = "sexe")
    private Sexe sexe;
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "E-mail invalide")
    private String email;
    @NotNull(message = "vous devez entrer un mot de passe.")
    @Column(name = "mot_passe")
    private String mot_passe;
    @Range(min = 600000000, max = 699999999, message = "numéro de téléphone invalide")
    @NotNull(message = "vous devez entrer un numero de téléphone")
    @Column(name = "numerotelephone")
    private int numerotelephone;
    @NotNull(message = "vous devez entrer un Pays")
    @Column(name = "pays")
    private String pays;
    @NotNull(message = "vous devez entrer une ville")
    @Column(name = "ville")
    private String ville;
    @Size(min = 3, max = 25, message = "vous devez entrer un quartier")
    @NotNull(message = "vous devez entrer un quartier")
    @Column(name = "quartier")
    private String quartier;



    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom,Sexe sexe, String email, String mot_passe, int numerotelephone,String pays, String ville, String quartier){

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

    public @NotNull Sexe getSexe() {
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

    public @NotNull String getMot_passe() {return mot_passe;}



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
                ", sexe="+sexe+
                ", email="+email+
                ", numerotelephone="+numerotelephone+
                ", pays="+pays+
                ", ville="+ville+
                ", quartier="+quartier+
                "}";
    }
}
