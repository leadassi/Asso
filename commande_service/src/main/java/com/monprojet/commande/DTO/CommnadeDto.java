package com.monprojet.commande.DTO;

import com.monprojet.commande.enums.Statut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommnadeDto {
        private int idCommande;
        private Date date;
        private double prixTotal;
        private double montantLivraison;
        private Statut  statutCommande;
        private int idUtilisateur;
        private int idPanier;
    }
