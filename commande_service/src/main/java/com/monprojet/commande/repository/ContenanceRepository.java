package com.monprojet.commande.repository;

import com.monprojet.commande.models.Contenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenanceRepository extends JpaRepository<Contenance,Integer> {
}
