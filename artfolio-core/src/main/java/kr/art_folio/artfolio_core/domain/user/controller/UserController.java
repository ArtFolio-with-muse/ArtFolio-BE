package kr.art_folio.artfolio_core.domain.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import kr.art_folio.artfolio_core.domain.user.dto.request.UserModifyRequest;
import kr.art_folio.artfolio_core.domain.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import kr.art_folio.artfolio_core.domain.user.service.UserService;
import kr.art_folio.artfolio_core.domain.user.dto.request.UserSignUpRequest;

@Tag(name = "User", description = "User 관련 API")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(
            @Parameter @Valid @RequestBody UserSignUpRequest signUpRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(signUpRequest));
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponse> modify(
            @Parameter @AuthenticationPrincipal UserDetails user,
            @Parameter @RequestPart(required = false) MultipartFile multipartFile,
            @Parameter @Valid @RequestPart UserModifyRequest userModifyRequest
    ) {
        return ResponseEntity.ok().body(userService.updateUser(user.getId(), multipartFile, userModifyRequest));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(
            @Parameter @Valid @PathVariable Long userId
    ) {
        userService.userDelete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @Parameter @Valid @PathVariable Long userId
    ) {
        return ResponseEntity.ok().body(userService.getUser(userId));
    }
}