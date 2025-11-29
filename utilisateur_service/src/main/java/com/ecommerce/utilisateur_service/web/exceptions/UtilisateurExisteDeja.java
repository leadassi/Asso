package com.ecommerce.utilisateur_service.web.exceptions;

public class UtilisateurExisteDeja extends RuntimeException {
    public UtilisateurExisteDeja(String message) {
        super(message);
    }
}
