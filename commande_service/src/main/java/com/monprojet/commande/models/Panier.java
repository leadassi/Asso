package com.monprojet.commande.models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.monprojet.commande.enums.Statut;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "Panier")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "contenances")

public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPanier;

    private int idUtilisateur; // Récupéré via l'API

    private double prixTotal;
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contenance> contenances;


}