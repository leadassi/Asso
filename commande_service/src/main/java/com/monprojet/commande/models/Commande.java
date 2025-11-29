package com.monprojet.commande.models;
import com.monprojet.commande.enums.Statut;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCommande;

    private Date date;
    private double prixTotal;
    private  int montant_livraison;

    @Enumerated(EnumType.STRING)
    private Statut statutCommande;

    private int idUtilisateur;

    @ManyToOne
    @JoinColumn(name = "id_panier", nullable = true)
    private Panier panier;

}

