package com.monprojet.commande.models;

import com.monprojet.commande.enums.Sexe;
import com.monprojet.commande.enums.Statut;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Utilisateur {

    private int id; // Nom de la colonne pour correspondre à la BD
    private String nom;
    private  String prenom;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    private String email;
    private String mot_passe;
    private int numerotelephone;
    private String pays;
    private String ville;
    private String quartier;
    private String bp;


}
