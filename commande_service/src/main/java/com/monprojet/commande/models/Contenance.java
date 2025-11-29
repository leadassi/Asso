package com.monprojet.commande.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = "panier")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Contenance")
public class Contenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContenance;

    @ManyToOne
    @JoinColumn(name = "idpanier", nullable = false)
    private Panier panier;

    private int idProduit; // Récupéré via l'API
    private int quantite = 1; // Valeur par défaut de 1
}
