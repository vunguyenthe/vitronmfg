package io.vitronmfg.application.repository;

import io.vitronmfg.application.domain.Career;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Career entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

}
