package kr.art_folio.artfolio_core.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "회원 가입 요청 정보를 담는 DTO")
public record UserSignUpRequest(

        @Schema(description = "사용자 이메일", example = "user@example.com")
        @Email
        @NotEmpty(message = "이메일은 필수 항목입니다.")
        String email,

        @Schema(description = "사용자 비밀번호", example = "userpassword")
        @NotEmpty(message = "비밀번호는 필수 항목입니다.")
        String password,

        @Schema(description = "비밀번호 확인", example = "userpassword")
        @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
        String confirmPassword,

        @Schema(description = "사용자 닉네임", example = "usernickname")
        @NotEmpty(message = "닉네임은 필수 항목입니다.")
        @Size(max = 30, message = "닉네임은 최대 30자까지 가능합니다.")
        String nickname
) {
}