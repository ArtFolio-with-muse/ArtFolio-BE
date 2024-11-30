package kr.art_folio.artfolio_core.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.art_folio.artfolio_core.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "회원 응답 정보 DTO")
public class UserResponse {

    @Schema(description = "사용자 ID", example = "1")
    private final Long id;

    @Schema(description = "사용자 이메일", example = "user@example.com")
    private final String email;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private final String profileImageUrl;

    @Schema(description = "사용자 닉네임", example = "usernickname")
    private final String nickname;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .build();
    }

    @Builder
    @Schema(description = "프로필 이미지 정보")
    public static class ProfileImage {

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
        private final String url;

        @Schema(description = "프로필 썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
        private final String thumbnailUrl;

        public static ProfileImage of(String imageUrl) {
            // 썸네일 URL 생성 로직 (예시)
            String thumbnailUrl = imageUrl != null
                    ? imageUrl.replace("profile", "thumbnail") // 단순 예제 로직
                    : null;
            return ProfileImage.builder()
                    .url(imageUrl)
                    .thumbnailUrl(thumbnailUrl)
                    .build();
        }
    }
}
