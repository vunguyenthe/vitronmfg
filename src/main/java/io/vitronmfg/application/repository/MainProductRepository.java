package io.vitronmfg.application.repository;

import io.vitronmfg.application.domain.MainProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MainProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MainProductRepository extends JpaRepository<MainProduct, Long> {

}
