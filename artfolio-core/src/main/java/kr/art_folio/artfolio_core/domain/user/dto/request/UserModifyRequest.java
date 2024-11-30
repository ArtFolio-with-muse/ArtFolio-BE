package kr.art_folio.artfolio_core.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "회원 수정 요청 DTO")
public record UserModifyRequest(

        @NotEmpty(message = "닉네임은 필수 항목입니다.")
        @Schema(description = "닉네임", example = "usernickname")
        String nickname,

        @Schema(description = "비밀번호", example = "userpassword")
        String password
) {
}
