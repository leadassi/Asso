package com.monprojet.commande.service;

import com.monprojet.commande.Client.ProduitRestClient;
import com.monprojet.commande.DTO.DetailProduitDto;
import com.monprojet.commande.DTO.FactureDto;
import com.monprojet.commande.controller.UpdateCommandeRequest;
import com.monprojet.commande.enums.Statut;
import com.monprojet.commande.exeptions.ResourceNotFoundException;
import com.monprojet.commande.models.*;
import com.monprojet.commande.repository.CommandeRepository;
import com.monprojet.commande.repository.PanierRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final PanierRepository panierRepository;
    private final PanierService panierService;
    private final ProduitRestClient produitRestClient;
    private final EmailService emailService;


    public Commande save(Commande commande) {
        Optional<Commande> existantCMd = getCmd(commande.getIdCommande());
        if (existantCMd.isEmpty()) {
            return commandeRepository.save(commande);
        }
        throw new RuntimeException("Cette commande existe deja pour");
    }

    public List<Commande> getAllCmd() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> getCmd(int id) {
        return commandeRepository.findById(id);
    }

    public int saveCmd(Commande commande){
        Commande commande1 = commandeRepository.save(commande);
        return commande1.getIdCommande() ;
    }



    public void creerCommande(Panier panier, Utilisateur utilisateur) {
        // Création de la commande
        Commande commande = new Commande();

        // Calculer le montant total de la commande
        double montantProduits = panierService.calculerPrixTotalFacture(panier.getIdPanier());
        double montantLivraison = 10; // Exemple : Montant de livraison fixe ou calculé dynamiquement
        double montantTotal = Math.ceil((montantProduits + montantLivraison) / 5) * 5; // Arrondir au multiple de 5 supérieur

        // Préparer les détails des produits pour FactureDto
        List<DetailProduitDto > detailsProduits = new ArrayList<>();
        for (Contenance contenance : panier.getContenances()) {
            // Récupérer les informations du produit via ProduitRestClient
            Produit produit = produitRestClient.findProduitById(contenance.getIdProduit());

            // Ajouter le détail du produit à la liste
            detailsProduits.add(new DetailProduitDto(
                    produit.getName(),
                    contenance.getQuantite(),
                    produit.getPrice(),
                    produit.getPrice() * contenance.getQuantite()
            ));
        }

        // Construire l'objet FactureDto
        FactureDto  factureDto = new FactureDto();
        factureDto.setNomUtilisateur(utilisateur.getNom());
        factureDto.setNumeroTelephone(utilisateur.getNumerotelephone());
        factureDto.setDetailsProduits(detailsProduits);
        factureDto.setMontantTotal(montantTotal);
        factureDto.setMontantLivraison(montantLivraison);

        // Sauvegarder la commande
        commande.setDate(new Date());
        commande.setPrixTotal(montantTotal);
        commande.setMontant_livraison((int) montantLivraison);
        commande.setStatutCommande(Statut.NULL); // Par défaut
        commande.setIdUtilisateur(utilisateur.getId());
        commande.setPanier(panier);

        commandeRepository.save(commande);

        // Afficher ou traiter l'objet FactureDto si nécessaire (par ex., logger)
        System.out.println("Facture générée : " + factureDto);
    }




    public Commande updateCommande(int id, Statut statut) {
        System.out.println("id: " + id);
        Commande commande = commandeRepository.findByIdCommande(id);

        // Mise à jour des champs
        commande.setStatutCommande(statut);
        return commandeRepository.save(commande);
    }
    public List<Commande> getCommandesByUtilisateur(int idUtilisateur) {
        return commandeRepository.findByIdUtilisateur(idUtilisateur);
    }
}

