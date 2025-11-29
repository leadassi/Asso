package com.monprojet.commande.controller;

import com.monprojet.commande.Client.UtilisateurRestClient;
import com.monprojet.commande.DTO.CommnadeDto;
import com.monprojet.commande.models.Commande;
import com.monprojet.commande.models.Utilisateur;
import com.monprojet.commande.repository.CommandeRepository;
import com.monprojet.commande.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/commande")
@CrossOrigin("*")
public class CommandeController {
    @Autowired
    private final CommandeService commandeService;
    @Autowired
    private  UtilisateurRestClient utilisateurRestClient;

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping("/historique-commandes")
    public List<Commande> getAllCommandes() {
        return commandeService.getAllCmd();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandesById(@PathVariable int id) {
        return commandeService.getCmd(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/2/{id}")
    public ResponseEntity<CommnadeDto> getCommandesById2(@PathVariable int id) {
        return commandeService.getCmd(id)
                .map(commande -> {
                    CommnadeDto commandeDTO = new CommnadeDto();
                    commandeDTO.setIdCommande(commande.getIdCommande());
                    commandeDTO.setDate(commande.getDate());
                    commandeDTO.setPrixTotal(commande.getPrixTotal());
                    commandeDTO.setMontantLivraison(commande.getMontant_livraison() );
                    commandeDTO.setStatutCommande(commande.getStatutCommande());
                    commandeDTO.setIdUtilisateur(commande.getIdUtilisateur());
                    commandeDTO.setIdPanier(commande.getPanier().getIdPanier());
                    return ResponseEntity.ok(commandeDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/enregistrerCmd")
    public Commande createCommande(@RequestBody Commande commande) {

        return commandeService.save(commande);
    }
   @PostMapping("/validercmd")
   public ResponseEntity<Integer> validerCmd(@RequestBody Commande commande) {
       // Vérification des champs obligatoires
       if (commande.getIdUtilisateur() == 0 || commande.getPrixTotal() <= 0) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
   
       // Vérification spécifique pour le panier
       if (commande.getPanier() == null || commande.getPanier().getIdPanier() == 0) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }

    try {
           // Appel du service pour sauvegarder la commande
                   int id = commandeService.saveCmd(commande);
                   return ResponseEntity.ok(id);
               } catch (Exception e) {
                   // Gestion des erreurs et retour d'une réponse avec code 500
                   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
               }
           }









    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCommande(@PathVariable int id, @RequestBody UpdateCommandeRequest request) {
        Commande updatedCommande = commandeService.updateCommande(id, request.getStatut());
        Map<String, Object> response=new HashMap<>();
        response.put("message","order update successfully");
        response.put("status",updatedCommande.getStatutCommande());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/utilisateur/{idUtilisateur}")
       public ResponseEntity<List<Commande>> getCommandesByUtilisateur ( @PathVariable int idUtilisateur){
           List<Commande> commandes = commandeService.getCommandesByUtilisateur(idUtilisateur);
           return ResponseEntity.ok(commandes);
       }


   }