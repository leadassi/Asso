package com.monprojet.commande.repository;

import com.monprojet.commande.models.Commande;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
Commande findByIdCommande(int idCommande);

    @Override
    List<Commande> findAll();

    List<Commande> findByIdUtilisateur(int idUtilisateur);

}
