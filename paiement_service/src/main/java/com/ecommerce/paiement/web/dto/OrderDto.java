package com.ecommerce.paiement.web.dto;

import java.util.List;

public class OrderDto {
    private int idCommande;
    private int idUtilisateur;
    private int idPanier;
    private double prixTotal;
    private int montant_livraison;

    public int getidCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public void setBillId(int order_id) {
        this.idCommande = order_id;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public float getMontant_livraison() {
        return montant_livraison;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setMontant_livraison(int montant_livraison) {
        this.montant_livraison = montant_livraison;
    }

}
