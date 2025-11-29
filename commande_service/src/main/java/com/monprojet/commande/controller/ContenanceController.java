package com.monprojet.commande.controller;

import com.monprojet.commande.Client.UtilisateurRestClient;
import com.monprojet.commande.models.Contenance;
import com.monprojet.commande.models.ContenanceDTO;
import com.monprojet.commande.models.Panier;
import com.monprojet.commande.models.Utilisateur;
import com.monprojet.commande.repository.ContenanceRepository;
import com.monprojet.commande.repository.PanierRepository;
import com.monprojet.commande.service.ContenanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commande/contenances")
@CrossOrigin("*")
public class ContenanceController {

    @Autowired
    private ContenanceService contenanceService;
    @Autowired
    private ContenanceRepository contenanceRepository;
    @Autowired
    private PanierRepository panierRepository;

    private ContenanceDTO contenanceDTO;
    @Autowired
    private UtilisateurRestClient utilisateurRestClient;


    // Endpoint pour récupérer toutes les contenances
    @GetMapping
    public List<Contenance> getAllContenances() {
        return contenanceRepository.findAll();
    }

    // Endpoint pour récupérer une contenance par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Contenance> getContenanceById(@PathVariable int id) {
        Optional<Contenance> contenance = contenanceRepository.findById(id);
        if (contenance.isPresent()) {
            return ResponseEntity.ok(contenance.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/afficheruser/{idUtilisateur}")
    public Utilisateur afficher(@PathVariable("idUtilisateur") int idUtilisateur) {
        Utilisateur utilisateur = utilisateurRestClient.findUserById(idUtilisateur);
        return utilisateur;
    }

    @PostMapping("/enregistrer")
    public ResponseEntity<?> createContenances(@RequestBody List<ContenanceDTO> requests) {
        try {
            System.out.println("Reçu : " + requests); // Log des données reçues
            List<Contenance> savedContenances = new ArrayList<>();

            for (ContenanceDTO request : requests) {
                System.out.println("Traitement du DTO : " + request); // Log du DTO actuel

                // Vérifiez si le panier existe
                Optional<Panier> panierOpt = panierRepository.findById(request.getIdPanier());
                if (!panierOpt.isPresent()) {
                    System.out.println("Panier introuvable pour l'ID : " + request.getIdPanier());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Panier non trouvé pour l'ID: " + request.getIdPanier());
                }

                // Création et sauvegarde de Contenance
                Contenance contenance = new Contenance();
                contenance.setPanier(panierOpt.get());
                contenance.setIdProduit(request.getIdProduit());
                contenance.setQuantite(request.getQuantite());
                savedContenances.add(contenanceRepository.save(contenance));
            }

            System.out.println("Contenances sauvegardées : " + savedContenances); // Log de l'opération réussie
            return ResponseEntity.status(HttpStatus.CREATED).body(savedContenances);
        } catch (Exception e) {
            e.printStackTrace(); // Log de l'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création des contenances : " + e.getMessage());
        }
    }






    // Endpoint pour mettre à jour une contenance existante
    @PutMapping("/{id}")
    public ResponseEntity<Contenance> updateContenance(@PathVariable int id, @RequestBody Contenance contenance) {
        Optional<Contenance> existingContenance = contenanceRepository.findById(id);
        if (existingContenance.isPresent()) {
            contenance.setIdContenance(id); // Assurez-vous de garder l'ID
            Contenance updatedContenance = contenanceRepository.save(contenance);
            return ResponseEntity.ok(updatedContenance);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint pour supprimer une contenance par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContenance(@PathVariable int id) {
        Optional<Contenance> contenance = contenanceRepository.findById(id);
        if (contenance.isPresent()) {
            contenanceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
