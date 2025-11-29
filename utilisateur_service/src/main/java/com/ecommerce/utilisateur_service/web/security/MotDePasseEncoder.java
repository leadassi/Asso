package com.ecommerce.utilisateur_service.web.security;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class MotDePasseEncoder {
    public static String encode(String mot_passe) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(mot_passe);
    }

    public static boolean matches(String mot_passe, String encoded_mot_passe) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(mot_passe, encoded_mot_passe);
    }
}
