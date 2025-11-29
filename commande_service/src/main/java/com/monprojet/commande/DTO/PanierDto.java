package com.monprojet.commande.DTO;

import com.monprojet.commande.models.ContenanceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PanierDto {

        private int idPanier;
        private int idUtilisateur;
        private double prixTotal;
        private List<ContenanceDTO> contenances;
}
