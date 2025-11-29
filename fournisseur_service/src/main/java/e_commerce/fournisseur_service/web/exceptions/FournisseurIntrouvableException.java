package e_commerce.fournisseur_service.web.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FournisseurIntrouvableException extends RuntimeException{
    public FournisseurIntrouvableException(String S){
        super(S);
    }
}
