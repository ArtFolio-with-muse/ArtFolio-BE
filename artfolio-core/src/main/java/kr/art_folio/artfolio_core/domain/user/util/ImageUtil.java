package kr.art_folio.artfolio_core.domain.user.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class ImageUtil {

    // 기본 이미지 저장 경로
    private static final String IMAGE_BASE_PATH = "uploads/images/";

    /**
     * 이미지 URL 생성
     * @param file MultipartFile 이미지 파일
     * @return 생성된 이미지 URL
     */
    public String getImageUrl(MultipartFile file) {
        // 파일 이름 고유화 처리 (UUID 사용)
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID() + "." + fileExtension;

        return IMAGE_BASE_PATH + uniqueFilename;
    }

    /**
     * 파일 경로 생성
     * @param imageUrl 이미지 URL
     * @return Path 객체
     */
    public Path makeFilePath(String imageUrl) {
        return Paths.get(imageUrl);
    }

    /**
     * 이미지 파일 저장
     * @param path 저장할 경로
     * @param file MultipartFile 이미지 파일
     */
    public void writeImageFile(Path path, MultipartFile file) {
        try {
            // 디렉토리 생성 (존재하지 않을 경우)
            Files.createDirectories(path.getParent());
            // 파일 저장
            Files.write(path, file.getBytes());
            log.info("이미지 파일 저장 완료: {}", path.toAbsolutePath());
        } catch (IOException e) {
            log.error("이미지 파일 저장 실패: {}", path.toAbsolutePath(), e);
            throw new RuntimeException("이미지 파일 저장 중 오류가 발생했습니다.");
        }
    }

    /**
     * 이미지 파일 삭제
     * @param path 삭제할 파일 경로
     */
    public void deleteImageUrl(Path path) {
        try {
            if (Files.exists(path)) {
                Files.delete(path);
                log.info("이미지 파일 삭제 완료: {}", path.toAbsolutePath());
            } else {
                log.warn("이미지 파일이 존재하지 않습니다: {}", path.toAbsolutePath());
            }
        } catch (IOException e) {
            log.error("이미지 파일 삭제 실패: {}", path.toAbsolutePath(), e);
            throw new RuntimeException("이미지 파일 삭제 중 오류가 발생했습니다.");
        }
    }

    /**
     * 파일 확장자 추출
     * @param filename 파일 이름
     * @return 파일 확장자 (예: jpg, png)
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("파일 이름에 확장자가 없습니다: " + filename);
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
}
