package kr.art_folio.artfolio_core.domain.user.service;

import kr.art_folio.artfolio_core.domain.user.dto.request.UserModifyRequest;
import kr.art_folio.artfolio_core.domain.user.dto.request.UserSignUpRequest;
import kr.art_folio.artfolio_core.domain.user.dto.response.UserResponse;
import kr.art_folio.artfolio_core.domain.user.entity.Role;
import kr.art_folio.artfolio_core.grobal.common.entity.Status;
import kr.art_folio.artfolio_core.domain.user.entity.User;
import kr.art_folio.artfolio_core.domain.user.repository.UserRepository;
import kr.art_folio.artfolio_core.domain.user.util.ImageUtil;
import kr.art_folio.artfolio_core.grobal.custom.CustomException;
import kr.art_folio.artfolio_core.grobal.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ImageUtil imageUtil;

    /**
     * 회원 가입
     */
    @Transactional
    public Long createUser(UserSignUpRequest userSignUpRequest) {
        // 이메일 중복 확인
        validateDuplicateEmail(userSignUpRequest.email());

        // 비밀번호 유효성 검사 및 암호화
        String encodedPassword = encoder.encode(userSignUpRequest.password());

        // User 엔티티 생성
        User user = User.builder()
                .email(userSignUpRequest.email())
                .nickname(userSignUpRequest.nickname())
                .password(encodedPassword)
                .role(Role.ARTIST)
                .build();

        // User 저장
        User savedUser = userRepository.save(user);

        // 저장된 User ID 반환
        return savedUser.getId();
    }


    /**
     * 회원 수정
     */
    @Transactional
    public UserResponse updateUser(Long userId, MultipartFile file, UserModifyRequest userModifyRequest) {
        User user = findActiveUserById(userId);

        // 패스워드 갱신
        String updatedPassword = updatePassword(user, userModifyRequest.password());

        // 이미지 처리
        String updatedImageUrl = updateImage(user, file);

        // 사용자 정보 업데이트
        user.updateInfo(userModifyRequest.nickname(), updatedPassword, updatedImageUrl);

        return UserResponse.of(user);
    }


    private String updatePassword(User user, String newPassword) {
        if (newPassword != null && !encoder.matches(newPassword, user.getPassword())) {
            return encoder.encode(newPassword);
        }
        return user.getPassword();
    }

    private String updateImage(User user, MultipartFile file) {
        if (file == null) {
            return user.getProfileImageUrl(); // 기존 이미지 유지
        }

        if (file.isEmpty()) {
            deleteImageFile(user.getProfileImageUrl());
            return null; // 이미지 제거
        }

        String imageUrl = imageUtil.getImageUrl(file);
        Path imagePath = imageUtil.makeFilePath(imageUrl);

        // 기존 이미지 삭제 및 새 이미지 저장
        deleteImageFile(user.getProfileImageUrl());
        imageUtil.writeImageFile(imagePath, file);

        return imageUrl;
    }

    private void deleteImageFile(String imageUrl) {
        if (imageUrl != null) {
            Path imagePath = Path.of(imageUrl);
            imageUtil.deleteImageUrl(imagePath);
        }
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = findActiveUserById(userId);
        deleteImageFile(user.getProfileImageUrl());
        user.delete();
    }

    /**
     * 회원 조회
     */
    public UserResponse getUser(Long userId) {
        User user = findActiveUserById(userId);
        return UserResponse.of(user);
    }

    private User findActiveUserById(Long userId) {
        return userRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다. ID: " + userId));
    }

    private void validateDuplicateEmail(String email) {
        userRepository.findByEmailAndRoleAndStatus(email, Role.ARTIST, Status.ACTIVE)
                .ifPresent(user -> {
                    throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 사용 중인 이메일입니다: " + email);
                });
    }

    private void validateDuplicateNickname(String nickname) {
        userRepository.findByNicknameAndStatus(nickname, Status.ACTIVE)
                .ifPresent(user -> {
                    throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 사용 중인 닉네임입니다: " + nickname);
                });
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "비밀번호는 최소 8자 이상이어야 합니다.");
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "비밀번호에는 적어도 하나의 특수 문자가 포함되어야 합니다.");
        }
    }
}
