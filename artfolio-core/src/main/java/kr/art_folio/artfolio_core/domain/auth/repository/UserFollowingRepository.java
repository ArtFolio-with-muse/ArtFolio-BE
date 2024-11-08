package kr.art_folio.artfolio_core.domain.auth.repository;

import kr.art_folio.artfolio_core.domain.auth.entity.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowingRepository extends JpaRepository<UserFollowing, Long> {
}
