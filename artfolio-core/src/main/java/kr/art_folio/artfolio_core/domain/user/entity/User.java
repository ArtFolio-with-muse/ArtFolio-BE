package kr.art_folio.artfolio_core.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import kr.art_folio.artfolio_core.grobal.common.entity.Status;
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

    @Column(name = "profile_image_url")
    private String description;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    //== 생성 메서드 ==//
    public static kr.art_folio.artfolio_core.domain.user.entity.User user(String email, String nickname, String password) {
        kr.art_folio.artfolio_core.domain.user.entity.User user = new kr.art_folio.artfolio_core.domain.user.entity.User();
        user.password = password;
        user.nickname = nickname;
        user.profileImageUrl = null;
        user.role = Role.ARTIST;
        user.status = Status.ACTIVE;
        return user;
    }

    /**
     * 사용자 정보 업데이트
     * @param nickname 새로운 닉네임
     * @param password 새로운 비밀번호
     * @param imageUrl 새로운 이미지 URL
     */
    public void updateInfo(String nickname, String password, String imageUrl) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
        this.profileImageUrl = profileImageUrl; // 이미지 URL은 null 허용 (삭제 가능)
    }

    /**
     * 사용자 삭제 처리
     */
    public void delete() {
        this.status = Status.DELETE; // 상태를 DELETED로 변경
    }
}
