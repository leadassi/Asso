package com.ecommerce.utilisateur_service.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ecommerce.utilisateur_service.model.Utilisateur;
import java.util.List;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur,Integer > {
    List<Utilisateur> findAll();
    Utilisateur findById(int id);
    Utilisateur save(Utilisateur utilisateur);
    void deleteById(Integer integer);
    boolean existsById(Integer integer);
    Utilisateur findByEmail(String email);
    boolean existsByEmail(String email);

}

