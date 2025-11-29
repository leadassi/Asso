package e_commerce.fournisseur_service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "fournisseurs")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 3, max = 25, message = "nom invalide")
    @NotNull(message = "vous devez remplir un nom")
    @Column(name = "nom")
    private String nom;
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "E-mail invalide")
    private String email;
    @Range(min = 600000000, max = 699999999, message = "numéro de téléphone invalide")
    @NotNull(message = "vous devez entrer un numero de téléphone")
    @Column(name = "numerotelephone")
    private int numerotelephone;
    @NotNull(message = "vous devez entrer un Pays")
    @Column(name = "pays")
    private String pays;
    @NotNull(message = "vous devez entrer une ville")
    @Column(name = "ville")
    private String ville;
    @Size(min = 5, max = 25, message = "vous devez entrer un quartier")
    @NotNull(message = "vous devez entrer un quartier")
    @Column(name = "quartier")
    private String quartier;
    @Size(min = 5, max = 5, message = "vous avez entrer une boite postale invalide")
    @NotNull(message = "vous devez entrer votre boite postale")
    @Column(name = "BP")
    private String BP;



    public Fournisseur() {
    }

    public Fournisseur(String nom, String email, int numerotelephone, String pays, String ville, String quartier, String BP){

        this.nom=nom;
        this.email=email;
        this.numerotelephone=numerotelephone;
        this.pays=pays;
        this.ville=ville;
        this.quartier=quartier;
        this.BP=BP;

    }

    //Getters

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public int getNumerotelephone() {
        return numerotelephone;
    }

    public String getPays() {return pays;}

    public String getVille() {return ville;}

    public String getQuartier() {return quartier;}

    public String getBP() {
        return BP;
    }


    //Setters

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumerotelephone(int numerotelephone) {
        this.numerotelephone = numerotelephone;
    }

    public void setPays(String pays) {this.pays = pays;}

    public void setVille(String ville) {this.ville = ville;}

    public void setQuartier(String quartier) {this.quartier = quartier;}

    public void setBP(String BP) {
        this.BP = BP;
    }

    @Override
    public String toString(){

        return "Utilisateur{"+
                "id="+id+
                ", nom="+nom+
                ", email="+email+
                ", numerotelephone="+numerotelephone+
                ", pays="+pays+
                ", ville="+ville+
                ", quartier="+quartier+
                ", BP="+BP+
                "}";
    }
}
