package kr.art_folio.artfolio_core.grobal.custom;

import kr.art_folio.artfolio_core.grobal.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
