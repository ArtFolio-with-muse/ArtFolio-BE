package kr.art_folio.artfolio_core.domain.auth.repository;

import kr.art_folio.artfolio_core.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
