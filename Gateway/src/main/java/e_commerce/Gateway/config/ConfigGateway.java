package e_commerce.Gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class ConfigGateway {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("produitService", r -> r.path("/produitService/**")
                        .uri("http://localhost:8080"))
                .route("utilisateur_service", r -> r.path("/Utilisateurs/**")
                        .uri("http://192.168.107.193:9091"))
                .route("gestion_livraison", r -> r.path("/Livraisonservices/**")
                        .uri("http://192.168.88.77:8000"))
                .route("fournisseur_service", r -> r.path("/Fournisseurs/**")
                        .uri("http://192.168.107.193:9092"))
                .route("commande", r -> r.path("/commande/**")
                        .uri("http://192.168.203.234:8081"))
                .route("paiement", r -> r.path("/payments/**")
                        .uri("http://192.168.88.32:9090"))
                .route("recommandation_system", r -> r.path("/recommandations/**")
                        .uri("http://192.168.88.32:8082"))
                .build();
    }

}