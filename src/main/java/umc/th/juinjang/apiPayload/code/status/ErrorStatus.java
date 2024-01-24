package umc.th.juinjang.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

    // Member Error

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),

    // Limjang Error
    LIMJANG_POST_REQUEST_NULL(HttpStatus.BAD_REQUEST, "LIMJANG400", "입력 값이 모두 넘어오지 않았습니다. 누락된 값이 있는지 다시 확인해주세요."),
    LIMJANG_POST_TYPE_ERROR(HttpStatus.BAD_REQUEST, "LIMJANG400", "거래목적, 매물유형, 가격유형 입력값 중 하나가 정해지지 않은 값입니다. 다시 확인해주세요."),
    LIMJANG_POST_PRICE_ERROR(HttpStatus.BAD_REQUEST, "LIMJANG400", "전달된 가격이 잘못되었습니다. 입력값을 확인해주세요."),

    // scrap
    _SCRAP_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "해당 게시글이 DB에 존재하지 않습니다. 관리자에게 문의 바랍니다."),
    _SCRAP_SCRAP_FAILD(HttpStatus.INTERNAL_SERVER_ERROR, "SCRAP4004", "스크랩 실패. 재시도하거나 관리자에게 문의 바랍니다."),

    CHECKLIST_TYPE_ERROR(HttpStatus.BAD_REQUEST, "CHECKLIST400", "정해지지 않은 요청값입니다. 다시 확인해주세요."),
      
    //JWT 토큰 에러
    TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "TOKEN400", "유효하지 않거나 만료된 토큰입니다."),
    //JWT 토큰 에러
    TOKEN_EMPTY(HttpStatus.BAD_REQUEST, "TOKEN401", "토큰값이 존재하지 않습니다.");
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}