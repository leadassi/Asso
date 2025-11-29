package com.ecommerce.utilisateur_service.config;





import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Active CORS pour les endpoints sécurisés
                .and()
                .csrf(csrf -> csrf
                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository()) // Utilise un cookie pour transmettre le token CSRF
                        .ignoringRequestMatchers("/public/**") // Exclure certains endpoints du CSRF
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/**").permitAll() // Autorise toutes les requêtes GET
                        .requestMatchers(HttpMethod.POST, "/inscription").permitAll() // Autorise POST /inscription
                        .requestMatchers(HttpMethod.PUT, "/**").authenticated() // Les autres PUT nécessitent une authentification
                        .requestMatchers(HttpMethod.DELETE, "/**").authenticated() // DELETE nécessite une authentification
                        .requestMatchers(HttpMethod.GET, "/Utilisateurs/csrf-token").permitAll()
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                )

                .httpBasic(); // Utilise l'authentification HTTP Basic

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Un utilisateur en mémoire avec un mot de passe crypté
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password123")) // Hachage du mot de passe
                .build();
        
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}





