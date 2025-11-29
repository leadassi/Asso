package com.ecommerce.utilisateur_service.email;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Data
@Service
public class EmailService {

    /*@Value("${spring.mail.username}")
    private String FromEmail;

        @Value("${mailersend.api.key}")
        private String apiKey;

        private final Map<String, Integer> verificationCodes = new HashMap<>();

        public int generateVerificationCode(String email) {
            Random random = new Random();
            int code = 100000 + random.nextInt(900000); // Génère un code à 6 chiffres
            verificationCodes.put(email, code);
            return code;
        }

        public void sendVerificationEmail(String email, int code) {
            MailerSend mailerSend = new MailerSend();
            mailerSend.setToken(apiKey);

            Email mail = new Email();
            mail.setFrom("Asso","no-reply@trial-351ndgwe1rdgzqx8.mlsender.net");
            mail.addRecipient("user",email);
            mail.setSubject("Votre code de vérification");
            mail.setHtml("<p>Votre code de vérification est : <strong>" + code + "</strong></p>");

            try {
                mailerSend.emails().send(mail);
                System.out.println("E-mail envoyé avec succès à : " + email);
            } catch (MailerSendException e) {

                System.err.println("Erreur d'envoi d'e-mail : " + e.getMessage());
                e.printStackTrace();
            }
        }

        public boolean verifyCode(String email, int code) {
            return verificationCodes.containsKey(email) && verificationCodes.get(email) == code;
        }*/





    @Autowired
    private JavaMailSender mailSender;

    private Map<String, Integer> verificationCodes = new HashMap<>();

    public int generateVerificationCode(String email) {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Génère un code à 6 chiffres
        verificationCodes.put(email, code);
        return code;
    }

    public void sendVerificationEmail(String email, int code) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("assoaddkn@gmail.com");
        message.setTo(email);
        message.setSubject("Votre code de vérification");
        message.setText("Bienvenu cher utilisateur, \n\n\n"+"\t\tVotre code de vérification est : " + code+"\n\n\n"+"Asso.");
        mailSender.send(message);
    }

    public boolean verifyCode(String email, int code) {
        return verificationCodes.containsKey(email) && verificationCodes.get(email) == code;
    }

}






