package kr.art_folio.artfolio_core.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId; // 유니크 값

    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    private String nickname; // 유니크 값

    private String profileImageUrl;

    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    //== 생성 메서드 ==//
    public static User user(String email, String nickname, String password) {
        User user = new User();
        user.password = password;
        user.nickname = nickname;
        user.profileImageUrl = null;
        user.role = Role.ARTIST;
        user.status = Status.ACTIVE;
        return user;
    }

}
