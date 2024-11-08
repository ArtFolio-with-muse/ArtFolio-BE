package kr.art_folio.artfolio_core.domain.comment.repository;

import kr.art_folio.artfolio_core.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
