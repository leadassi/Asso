package com.monprojet.commande.controller;

import com.monprojet.commande.enums.Statut;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommandeRequest {
    private Statut statut;

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

}