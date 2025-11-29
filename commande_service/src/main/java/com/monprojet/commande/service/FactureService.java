package com.monprojet.commande.service;

import com.monprojet.commande.Client.ProduitRestClient;
import com.monprojet.commande.Client.UtilisateurRestClient;
import com.monprojet.commande.DTO.DetailProduitDto;
import com.monprojet.commande.DTO.FactureDto;
import com.monprojet.commande.exeptions.ResourceNotFoundException;
import com.monprojet.commande.models.*;
import com.monprojet.commande.repository.CommandeRepository;
import com.monprojet.commande.repository.PanierRepository;
import com.monprojet.commande.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactureService {

    @Autowired
    private ProduitRestClient produitRestClient;

    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private UtilisateurRestClient utilisateurRestClient;

    @Autowired
    private EmailService emailService;

    public FactureDto genererFacture(int idCommande) {
        // Récupération de la commande par ID
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Récupération du panier associé à la commande
        Panier panier = commande.getPanier();
        if (panier == null) {
            throw new RuntimeException("Aucun panier associé à cette commande");
        }

        // Récupération de l'utilisateur via son ID
        int idUtilisateur = commande.getIdUtilisateur();
        Utilisateur utilisateur = utilisateurRestClient.findUserById(idUtilisateur);

        // Construire les détails des produits
        List<DetailProduitDto> produits = panier.getContenances().stream()
                .map(contenance -> {
                    Produit produit = produitRestClient.findProduitById(contenance.getIdProduit());
                    double prixTotal = produit.getPrice() * contenance.getQuantite();
                    return new DetailProduitDto(
                            produit.getName(),
                            contenance.getQuantite(),
                            produit.getPrice(),
                            prixTotal
                    );
                })
                .collect(Collectors.toList());

        // Calculer le montant total (produits + livraison)
        double montantTotal = produits.stream()
                .mapToDouble(DetailProduitDto::getPrixTotal)
                .sum() + commande.getMontant_livraison() ;

        // Retourner le DTO de la facture
        return new FactureDto(
                utilisateur.getNom(),
                utilisateur.getNumerotelephone(),
                produits,
                montantTotal,
                commande.getMontant_livraison() ,
                utilisateur.getEmail()
        );
    }





    public Produit findProduitById(int idProduit) {
        // Simule un appel à une API externe pour obtenir un produit
        Produit produit = new Produit();
        produit.setPrice(50);  // Exemple de prix
        produit.setName("Produit " + idProduit);
        return produit;
    }





    public String genererContenuHtmlFacture(FactureDto factureDto) {
        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<head>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0; }");
        html.append(".cont { display: flex; justify-content: center; align-items: center; min-height: 100vh; }");
        html.append(".card { max-width: 600px; width: 100%; padding: 20px; border-radius: 10px; background-color: white; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        html.append(".header { background-color: #D97706; color: white; padding: 15px; text-align: center; border-radius: 8px 8px 0 0; font-size: 1.5em; }");
        html.append(".content { padding: 20px; }");
        html.append(".text-warning { color: #D97706; }");
        html.append(".table { width: 100%; margin-top: 15px; border-collapse: collapse; }");
        html.append(".table th, .table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        html.append(".table th { background-color: #f2f2f2; font-weight: bold; }");
        html.append(".btn { display: inline-block; padding: 10px 20px; margin-top: 20px; color: white; background-color: #D97706; text-align: center; text-decoration: none; border-radius: 5px; font-size: 1em; }");
        html.append(".btn:hover { background-color: #b45309; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class='cont'>");
        html.append("<div class='card'>");
        html.append("<div class='header'>Facture</div>");
        html.append("<div class='content'>");
        html.append("<p>Bonjour <span class='text-warning'>").append(factureDto.getNomUtilisateur()).append("</span>,</p>");
        html.append("<p>Voici les détails de votre commande :</p>");

        html.append("<table class='table'>");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>Produit</th>");
        html.append("<th>Quantité</th>");
        html.append("<th>Prix Unitaire</th>");
        html.append("<th>Total</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");
        for (DetailProduitDto produit : factureDto.getDetailsProduits()) {
            html.append("<tr>");
            html.append("<td>").append(produit.getNomProduit()).append("</td>");
            html.append("<td>").append(produit.getQuantite()).append("</td>");
            html.append("<td>").append(produit.getPrixUnitaire()).append(" francs</td>");
            html.append("<td>").append(produit.getPrixTotal()).append(" francs</td>");
            html.append("</tr>");
        }
        html.append("</tbody>");
        html.append("</table>");

        html.append("<p class='text-warning' style='margin-top: 20px; font-weight: bold;'>Montant Total : ").append(factureDto.getMontantTotal()).append(" francs</p>");
        html.append("<p class='text-warning' style='font-weight: bold;'>Frais de Livraison : ").append(factureDto.getMontantLivraison()).append(" francs</p>");
        html.append("<p class='text-warning' style='font-size: 1.2em; font-weight: bold;'>Total à Payer : ").append(factureDto.getMontantTotal() + factureDto.getMontantLivraison()).append(" francs</p>");
        html.append("</div>");
        html.append("</div>");
        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

}

