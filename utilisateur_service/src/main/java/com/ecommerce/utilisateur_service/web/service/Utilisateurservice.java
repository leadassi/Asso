package com.ecommerce.utilisateur_service.web.service;


import com.ecommerce.utilisateur_service.email.EmailService;
import com.ecommerce.utilisateur_service.model.Utilisateur;
import com.ecommerce.utilisateur_service.web.exceptions.UtilisateurIntrouvableException;
import com.ecommerce.utilisateur_service.web.security.MotDePasseEncoder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.ecommerce.utilisateur_service.model.Utilisateur;
import com.ecommerce.utilisateur_service.web.dao.UtilisateurDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class Utilisateurservice {


    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private EmailService emailService;

    // Méthode pour trouver un utilisateur par ID avec gestion de résilience

    @Retry(name = "utilisateurService", fallbackMethod = "fallbackGetUtilisateurById")
    @TimeLimiter(name = "utilisateurService")
    public CompletableFuture<Utilisateur> getUtilisateurById(Integer id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Utilisateur utilisateur = utilisateurDao.findById(id)
                        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
                return utilisateur;
            } catch (Exception e) {
                throw new CompletionException(e); // Transmet l'exception au Retry
            }
        });
    }

    // Méthode de fallback en cas d'échec après les tentatives de Retry
    public CompletableFuture<Utilisateur> fallbackGetUtilisateurById(Long id, Throwable throwable) {
        System.out.println("Echec de récupération de l'utilisateur après plusieurs tentatives : " + throwable.getMessage());
        return CompletableFuture.completedFuture(new Utilisateur("inconnu","inconnu", Utilisateur.Sexe.masculin, "inconnu@example.com", "Utilisateur Inconnu", 600000000, "CM", "inconnu", "inconnu")); // ou une réponse par défaut
    }

    /*@CircuitBreaker(name = "userService0", fallbackMethod = "fallbackFindUtilisateurById")
    @Retry(name = "userService")
    //les microservices ne vont vouloir récupérer l'utilisateur que par son id
    public Utilisateur findUtilisateurById(Integer id) {
        // Cette opération pourrait échouer si la base de données est indisponible
        return utilisateurDao.findById(id)
                .orElseGet(() -> fallbackFindUtilisateurById(id, new UtilisateurIntrouvableException("L'utilisateur avec l'id " + id + " est INTROUVABLE.")));
    }microservice-b

    // Méthode de secours en cas d'échec
    public Utilisateur fallbackFindUtilisateurById(Integer id, Throwable throwable) {
        System.out.println("Méthode de secours appelée : " + throwable.getMessage());

        // Retourne un utilisateur par défaut ou un message d'erreur
        return new Utilisateur("inconnu","inconnu", Utilisateur.Sexe.masculin, "inconnu@example.com", "Utilisateur Inconnu", 600000000, "CM", "inconnu", "inconnu","iiii0");
    }*/


    public void ajouterUtilisateur(@Valid @RequestBody Utilisateur utilisateur) {
        // Règle de gestion : Le nom de famille doit être mis en minuscule.
        utilisateur.setNom(utilisateur.getNom().toLowerCase());
        String motDePasseCrypte = MotDePasseEncoder.encode(utilisateur.getMot_passe());
        utilisateur.setMot_passe(motDePasseCrypte);
        utilisateurDao.save(utilisateur);
    }

    public boolean verifierMotDePasse(String motDePasse, String motDePasseCrypte) {
        return MotDePasseEncoder.matches(motDePasse, motDePasseCrypte);
    }

    public Utilisateur connexion(String email, String motDePasse) {
        // Étape 1: Chercher l'utilisateur par email
        Utilisateur utilisateur = utilisateurDao.findByEmail(email);

        // Étape 2: Vérifier si l'utilisateur existe et que le mot de passe est correct
        if (utilisateur != null && verifierMotDePasse(motDePasse, utilisateur.getMot_passe())) {
            return utilisateur; // Utilisateur authentifié avec succès
        }

        return null; // Connexion échouée
    }

    public List<Utilisateur> listeUtilisateurs(){
        return utilisateurDao.findAll();
    }

    public Utilisateur AffichageUtilisateur(int id){
        Utilisateur utilisateur=utilisateurDao.findById(id);
        if(utilisateur==null) throw new UtilisateurIntrouvableException("L'utilisateur avec l'id " + id + " est INTROUVABLE.");
        return utilisateur;
    }

    public void supprimerUtilisateur(@PathVariable int id) {
        utilisateurDao.deleteById(id);
    }

    public Utilisateur updateUtilisateur(@PathVariable int id,@RequestBody Utilisateur utilisateur)
    {
        Utilisateur utilisateur1=utilisateurDao.findById(id);
        if(utilisateur1 != null){
            utilisateur1.setNom(utilisateur.getNom());
            utilisateur1.setPrenom(utilisateur.getPrenom());
            utilisateur1.setNumerotelephone(utilisateur.getNumerotelephone());
            utilisateur1.setEmail(utilisateur.getEmail());
            utilisateur1.setMot_passe(utilisateur.getMot_passe());
            utilisateur1.setSexe(utilisateur.getSexe());
            utilisateur1.setVille(utilisateur.getVille());
            utilisateur1.setQuartier(utilisateur.getQuartier());
            return utilisateurDao.save(utilisateur1);
        }else{
            return null;
        }

    }

    public boolean utilisateurexiste(@PathVariable int id){
        return utilisateurDao.existsById(id);
    }


    public boolean emailexiste(@PathVariable String email){
        return utilisateurDao.existsByEmail(email);
    }

    public boolean sendVerificationEmailtest(String email, int code) {
        Utilisateur utilisateurOpt = utilisateurDao.findByEmail(email);
        if (utilisateurOpt != null) {
            emailService.sendVerificationEmail(email, code);
            return true;
        }
        return false;
    }

    public boolean reinitialiserMotDePasse(String email, String code, String nouveauMotDePasse) {
        Utilisateur utilisateurOpt = utilisateurDao.findByEmail(email);
        if (utilisateurOpt != null) {
            utilisateurOpt.setMot_passe(MotDePasseEncoder.encode(nouveauMotDePasse));
            utilisateurDao.save(utilisateurOpt);
            return true;
        }
        return false; // Retourne false si le code est incorrect ou expiré
    }



}
