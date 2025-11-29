package e_commerce.fournisseur_service.web.controller;


import e_commerce.fournisseur_service.model.Fournisseur;
import e_commerce.fournisseur_service.web.service.Fournisseurservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@Controller
//@RequestMapping("/api/fournisseurs")
@SessionAttributes("fournisseur")
public class FournisseurControler {


    @Autowired
    private Fournisseurservice fournisseurservice;


    //supprimer un utilisateur
    @DeleteMapping(value = "/Fournisseurs/{id}")
    @ResponseBody
    public ResponseEntity<Void> supprimerFournisseur(@PathVariable int id) {
        if (fournisseurservice.fournisseurexiste(id)) {
            fournisseurservice.supprimerFournisseur(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    //modifier les attributs d'un utilisateur
    @PutMapping(value = "/Fournisseurs/{id}")
    @ResponseBody
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable int id, @RequestBody Fournisseur fournisseur) {
        Fournisseur fournisseur1 = fournisseurservice.updateFournisseur(id, fournisseur);
        if (fournisseur1 != null) {
            return ResponseEntity.ok(fournisseur1);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //obtenir la liste des utilisateurs
    @GetMapping("/Fournisseurs")
    @ResponseBody
    public ResponseEntity<List<Fournisseur>> listeFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurservice.listeFournisseurs();
        if (!fournisseurs.isEmpty()) {
            return ResponseEntity.ok(fournisseurs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //obtenir les informations d'un utilisateur
    @GetMapping("/Fournisseurs/{id}")
    @ResponseBody
    public ResponseEntity<Fournisseur> AffichageFournisseur(@PathVariable int id) {
        Fournisseur fournisseur = fournisseurservice.AffichageFournisseur(id);
        if (fournisseur != null) {
            return ResponseEntity.ok(fournisseur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //créer un fournisseur
    @PostMapping(value = "/Fournisseurs")
    @ResponseBody
    public ResponseEntity<Fournisseur> ajouterFournisseur(@Valid @RequestBody Fournisseur fournisseur) {

        Fournisseur fournisseurAdded = fournisseurservice.ajouterFournisseur(fournisseur);

        if (Objects.isNull(fournisseurAdded)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(fournisseurAdded);

    }
}
