package kr.art_folio.artfolio_core.domain.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId; // 유니크 값

    private String password;

    private String name;

    private String nickname; // 유니크 값

    private String profileImageUrl;

    private String description;

}
