package com.monprojet.commande.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FactureDto {
    private String nomUtilisateur;
    private int numeroTelephone;
    private List<DetailProduitDto> detailsProduits;
    private double montantTotal;
    private double montantLivraison;
    private String emailUtilisateur;
}