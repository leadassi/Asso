package e_commerce.fournisseur_service.web.service;



import e_commerce.fournisseur_service.model.Fournisseur;
import e_commerce.fournisseur_service.web.exceptions.FournisseurIntrouvableException;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import e_commerce.fournisseur_service.web.dao.FournisseurDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class Fournisseurservice {


    @Autowired
    private FournisseurDao fournisseurDao;


    // Méthode pour trouver un utilisateur par ID avec gestion de résilience

    @Retry(name = "fournisseurService", fallbackMethod = "fallbackGetFournisseurById")
    @TimeLimiter(name = "fournisseurService")
    public CompletableFuture<Fournisseur> getFournisseurById(Integer id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Fournisseur fournisseur = fournisseurDao.findById(id)
                        .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + id));
                return fournisseur;
            } catch (Exception e) {
                throw new CompletionException(e); // Transmet l'exception au Retry
            }
        });
    }

    // Méthode de fallback en cas d'échec après les tentatives de Retry
    public CompletableFuture<Fournisseur> fallbackGetFournisseurById(Long id, Throwable throwable) {
        System.out.println("Echec de récupération du fournisseur après plusieurs tentatives : " + throwable.getMessage());
        return CompletableFuture.completedFuture(new Fournisseur("inconnu","inconnu@example.com",  600000000, "CM", "inconnu", "inconnu","iiii0")); // ou une réponse par défaut
    }

    public Fournisseur ajouterFournisseur(@Valid @RequestBody Fournisseur fournisseur) {
        // Règle de gestion : Le nom de famille doit être mis en minuscule.
        fournisseur.setNom(fournisseur.getNom().toLowerCase());
        return fournisseurDao.save(fournisseur);
    }


    public Fournisseur connexion(String email, String motDePasse) {
        // Étape 1: Chercher le fournisseur par email
        Fournisseur fournisseur = fournisseurDao.findByEmail(email);

        // Étape 2: Vérifier si l'utilisateur existe et que le mot de passe est correct
        if (fournisseur != null) {
            return fournisseur; // fournisseur authentifié avec succès
        }

        return null; // Connexion échouée
    }

    public List<Fournisseur> listeFournisseurs(){
        return fournisseurDao.findAll();
    }

    public Fournisseur AffichageFournisseur(@PathVariable int id){
        Fournisseur fournisseur=fournisseurDao.findById(id);
        if(fournisseur==null) throw new FournisseurIntrouvableException("Le fournisseur avec l'id " + id + " est INTROUVABLE.");
        return fournisseur;
    }

    public void supprimerFournisseur(@PathVariable int id) {
        fournisseurDao.deleteById(id);
    }

    public Fournisseur updateFournisseur(@PathVariable int id, @RequestBody Fournisseur fournisseur)
    {
        Fournisseur utilisateur1=fournisseurDao.findById(id);
        if(utilisateur1 != null){
            utilisateur1.setNom(fournisseur.getNom());
            utilisateur1.setNumerotelephone(fournisseur.getNumerotelephone());
            utilisateur1.setEmail(fournisseur.getEmail());
            utilisateur1.setBP(fournisseur.getBP());
            utilisateur1.setVille(fournisseur.getVille());
            utilisateur1.setQuartier(fournisseur.getQuartier());
            return fournisseurDao.save(utilisateur1);
        }else{
            return null;
        }

    }

    public boolean fournisseurexiste(@PathVariable int id){
        return fournisseurDao.existsById(id);
    }



}
