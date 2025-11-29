package com.ecommerce.recommandation_system.web.repository;

import com.ecommerce.recommandation_system.web.model.Recommandation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommandationRepository extends JpaRepository<Recommandation, Void> {
    List<Recommandation> findByProductId(int product_id);
}
