package io.vitronmfg.application.repository;

import io.vitronmfg.application.domain.Slide;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Slide entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

}
