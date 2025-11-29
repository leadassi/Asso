package com.monprojet.commande.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerEmailHtml(String email, String sujet, String contenuHtml) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("L'adresse e-mail ne peut pas être nulle ou vide.");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(sujet);
            helper.setText(contenuHtml, true);
            helper.setFrom("votre-adresse-email@example.com");

            mailSender.send(message);
            System.out.println("E-mail envoyé à : " + email + " avec le sujet : " + sujet);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
}
