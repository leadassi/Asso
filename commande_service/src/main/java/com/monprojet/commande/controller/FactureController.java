package com.monprojet.commande.controller;

import com.monprojet.commande.DTO.FactureDto;
import com.monprojet.commande.exeptions.ResourceNotFoundException;
import com.monprojet.commande.service.EmailService;
import com.monprojet.commande.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/commande/email")
@CrossOrigin("*")

public class FactureController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private FactureService factureService;

    @PostMapping("/envoyer-facture/{idCommande}")
    public ResponseEntity<String> envoyerFacture(@PathVariable int idCommande) {
        try {
            // Génération du DTO de la facture
            FactureDto factureDto = factureService.genererFacture(idCommande);

            // Génération du contenu HTML de la facture
            String contenuHtml = factureService.genererContenuHtmlFacture(factureDto);

            // Envoi de l'email contenant la facture
            emailService.envoyerEmailHtml(
                    factureDto.getEmailUtilisateur(),
                    "Votre facture",
                    contenuHtml
            );

            // Retourner une réponse réussie
            return ResponseEntity.ok("Facture envoyée avec succès à " + factureDto.getNomUtilisateur());
        } catch (ResourceNotFoundException e) {
            // Retourner une erreur si la ressource n'est pas trouvée
            return ResponseEntity.status(404).body("Ressource non trouvée : " + e.getMessage());
        } catch (Exception e) {
            // Attraper d'autres erreurs et retourner une réponse explicative
            return ResponseEntity.status(400).body("Erreur lors de l'envoi de la facture : " + e.getMessage());
        }
    }


}
