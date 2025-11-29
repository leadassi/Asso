package com.monprojet.commande.repository;

import com.monprojet.commande.enums.Statut;
import com.monprojet.commande.models.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Integer> {

}
