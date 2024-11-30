package kr.art_folio.artfolio_core.grobal.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    RESOURCE_NOT_FOUND("리소스를 찾을 수 없습니다."),
    DUPLICATE_OBJECT("중복된 객체입니다."),
    INVALID_INPUT("잘못된 입력값입니다.");

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

}
