package com.ecommerce.paiement.web.dto;

import java.util.List;

public class PanierDto {

    private int idPanier;
    private int idUtilisateur;
    private double prixTotal;
    private List<ContenanceDto> contenances;

    public int getIdPanier() {
        return idPanier;
    }

    public List<ContenanceDto> getContenances() {
        return contenances;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }
    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}