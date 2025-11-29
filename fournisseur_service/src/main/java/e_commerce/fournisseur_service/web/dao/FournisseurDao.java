package e_commerce.fournisseur_service.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import e_commerce.fournisseur_service.model.Fournisseur;
import java.util.List;

@Repository
public interface FournisseurDao extends JpaRepository<Fournisseur,Integer > {
    List<Fournisseur> findAll();
    Fournisseur findById(int id);
    Fournisseur save(Fournisseur utilisateur);
    void deleteById(Integer integer);
    boolean existsById(Integer integer);
    Fournisseur findByEmail(String email);


}

