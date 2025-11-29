package e_commerce.fournisseur_service.web.exceptions;

public class FournisseurExisteDeja extends RuntimeException {
    public FournisseurExisteDeja(String message) {
        super(message);
    }
}
