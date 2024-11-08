package kr.art_folio.artfolio_core.domain.exhibition.repository;

import kr.art_folio.artfolio_core.domain.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
}
