package kr.art_folio.artfolio_core.domain.user.repository;

import kr.art_folio.artfolio_core.domain.user.entity.*;
import kr.art_folio.artfolio_core.grobal.common.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByLoginId(String loginId);

    Optional<User> findByUserIdAndStatus(Long userId, Status status);

    Optional<User> findByEmailAndRoleAndStatus(String email, Role role, Status status);

    Optional<User> findByNicknameAndStatus(String nickname, Status status);
}
