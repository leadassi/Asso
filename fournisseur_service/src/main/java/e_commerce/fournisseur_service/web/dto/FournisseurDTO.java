package e_commerce.fournisseur_service.web.dto;

import e_commerce.fournisseur_service.model.Fournisseur;

public class FournisseurDTO {

    private Integer id;
    private String nom;
    private String email;
    private int numerotelephone;
    private String pays;
    private String ville;
    private String quartier;
    private String BP;

    public FournisseurDTO() {}

    // Constructeur pour transformer Utilisateur en UtilisateurDTO
    public FournisseurDTO(Fournisseur utilisateur) {
        this.id = utilisateur.getId();
        this.email = utilisateur.getEmail();
        this.nom = utilisateur.getNom();
        this.numerotelephone=utilisateur.getNumerotelephone();
        this.pays=utilisateur.getPays();
        this.BP=utilisateur.getBP();
        this.quartier=utilisateur.getQuartier();
        this.ville=utilisateur.getVille();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBP() {
        return BP;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public int getNumerotelephone() {
        return numerotelephone;
    }

    public void setNumerotelephone(int numerotelephone) {
        this.numerotelephone = numerotelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
