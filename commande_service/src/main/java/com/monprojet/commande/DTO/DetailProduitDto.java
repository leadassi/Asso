package com.monprojet.commande.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailProduitDto {
    private String nomProduit;
    private int quantite;
    private double prixUnitaire;
    private double prixTotal;

}