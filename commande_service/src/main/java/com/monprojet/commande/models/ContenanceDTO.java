package com.monprojet.commande.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContenanceDTO {
    private int idContenance;
    private int idPanier;
    private int idProduit;
    private int quantite;

}
