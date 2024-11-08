package kr.art_folio.artfolio_core.domain.auth.repository;

import kr.art_folio.artfolio_core.domain.auth.entity.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {
}
