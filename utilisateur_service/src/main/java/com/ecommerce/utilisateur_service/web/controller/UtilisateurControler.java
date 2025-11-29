package com.ecommerce.utilisateur_service.web.controller;

import com.ecommerce.utilisateur_service.email.EmailService;
import com.ecommerce.utilisateur_service.model.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.ecommerce.utilisateur_service.web.service.Utilisateurservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Utilisateurs")
//@CrossOrigin("*")
public class UtilisateurControler {

    @Autowired
    private Utilisateurservice utilisateurservice;

    @Autowired
    private EmailService emailService;

    @GetMapping("/csrf-token")
    public Map<String, String> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        Map<String, String> response = new HashMap<>();
        if (csrfToken != null) {
            response.put("token", csrfToken.getToken());
        } else {
            response.put("error", "CSRF token not found");
        }
        return response;
    }


    // Endpoint pour obtenir les informations de l'utilisateur connecté
   /* @GetMapping("/me")
    public ResponseEntity<Utilisateur> getCurrentUser(Authentication authentication, HttpServletRequest request) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //Utilisateur user = (Utilisateur) request.getSession().getAttribute("currentUser");
        // Le nom d'utilisateur est récupéré via l'objet Authentication
        String email = authentication.getName();
        System.out.println("Email récupéré : " + email);
        // Rechercher l'utilisateur dans la base par son nom d'utilisateur
        Utilisateur utilisateur = utilisateurservice.findByEmail(email);
        System.out.println("Utilisateur trouvé : " + utilisateur);
        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(utilisateur);
    }*/

    // Inscription
    @PostMapping("/inscription")
    public ResponseEntity<?> inscription(@Valid @RequestBody Utilisateur utilisateur, HttpSession session) {

        if (utilisateurservice.emailexiste(utilisateur.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Cet email est déjà utilisé."));
        }

        // Générer un code de vérification
        int code = emailService.generateVerificationCode(utilisateur.getEmail());
        emailService.sendVerificationEmail(utilisateur.getEmail(), code);
        session.setAttribute("utilisateur", utilisateur);
        // Retourner une réponse au front-end
        return ResponseEntity.ok(Map.of("message", "Un email de vérification a été envoyé.", "email", utilisateur.getEmail()));
    }

    // Vérification du code
    @PostMapping("/verification")
    public ResponseEntity<?> verification(@RequestParam("email") String email, @RequestParam("code") int code, HttpSession session) {

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (!email.equals(utilisateur.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "L'email fourni ne correspond pas à l'utilisateur en cours."));
        }

        if (emailService.verifyCode(email, code)) {
            // Sauvegarder l'utilisateur dans la base
            utilisateurservice.ajouterUtilisateur(utilisateur);

            return ResponseEntity.ok(Map.of("message", "Utilisateur enregistré avec succès.", "utilisateur", utilisateur));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Code de vérification incorrect."));
        }
    }

    // Connexion
    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody Map<String, String> credentials, HttpSession session) {
        String email = credentials.get("email");
        String mot_passe = credentials.get("mot_passe");

        Utilisateur utilisateur = utilisateurservice.connexion(email, mot_passe);

        if (utilisateur != null) {
            // Ajouter l'utilisateur à la session
            session.setAttribute("utilisateur", utilisateur);
            return ResponseEntity.ok(utilisateur);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Email ou mot de passe incorrect."));
        }
    }

    // Déconnexion
    @PostMapping("/deconnexion")
    public ResponseEntity<?> deconnexion(HttpSession session) {
        session.invalidate(); // Invalider la session
        return ResponseEntity.ok(Map.of("message", "Déconnecté avec succès."));
    }

    // Réinitialisation du mot de passe - Demande de code
    @PostMapping("/reconnexion")
    public ResponseEntity<?> processForgotPassword(@RequestParam String email) {
        int code = emailService.generateVerificationCode(email);
        boolean emailSent = utilisateurservice.sendVerificationEmailtest(email, code);

        if (emailSent) {
            return ResponseEntity.ok(Map.of("message", "Un code de réinitialisation a été envoyé à votre e-mail."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Adresse e-mail non trouvée."));
        }
    }

    // Réinitialisation du mot de passe - Validation
    @PostMapping("/reinscription")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        String nouveauMotDePasse = request.get("nouveauMotDePasse");

        boolean passwordReset = utilisateurservice.reinitialiserMotDePasse(email, code, nouveauMotDePasse);

        if (passwordReset) {
            return ResponseEntity.ok(Map.of("message", "Mot de passe réinitialisé avec succès."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Code incorrect ou expiré."));
        }
    }


    //supprimer un utilisateur
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable int id) {
        if (utilisateurservice.utilisateurexiste(id)) {
            utilisateurservice.supprimerUtilisateur(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    //modifier les attributs d'un utilisateur
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateur) {
        Utilisateur utilisateur1 = utilisateurservice.updateUtilisateur(id, utilisateur);
        if (utilisateur1 != null) {
            return ResponseEntity.ok(utilisateur1);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //obtenir la liste des utilisateurs
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Utilisateur>> listeUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurservice.listeUtilisateurs();
        if (!utilisateurs.isEmpty()) {
            return ResponseEntity.ok(utilisateurs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //obtenir les informations d'un utilisateur
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Utilisateur> AffichageUtilisateur(@PathVariable int id) {
        Utilisateur utilisateur = utilisateurservice.AffichageUtilisateur(id);
        if (utilisateur != null) {
            return ResponseEntity.ok(utilisateur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
