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
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "해당하는 사용자를 찾을 수 없습니다."),
    MEMBER_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4002", "사용자의 이메일이 없습니다."),
    MEMBER_NOT_FOUND_IN_KAKAO(HttpStatus.BAD_REQUEST, "MEMBER4003", "APPLE로 회원가입한 회원입니다."),
    ALREADY_LOGOUT(HttpStatus.BAD_REQUEST, "MEMBER4004", "이미 로그아웃 되었습니다."),

    // nickname 에러
    NICKNAME_EMPTY(HttpStatus.BAD_REQUEST, "NICKNAME4001", "닉네임이 존재하지 않습니다. 닉네임을 입력해주세요."),


    // Limjang Error
    LIMJANG_POST_REQUEST_NULL(HttpStatus.BAD_REQUEST, "LIMJANG4001", "입력 값이 모두 넘어오지 않았습니다. 누락된 값이 있는지 다시 확인해주세요."),
    LIMJANG_POST_TYPE_ERROR(HttpStatus.BAD_REQUEST, "LIMJANG4002", "거래목적, 매물유형, 가격유형 입력값 중 하나가 정해지지 않은 값입니다. 다시 확인해주세요."),
    LIMJANG_POST_PRICE_ERROR(HttpStatus.BAD_REQUEST, "LIMJANG4003", "전달된 가격이 잘못되었습니다. 입력값을 확인해주세요."),
    LIMJANG_NOTFOUND_ERROR(HttpStatus.BAD_REQUEST, "LIMJANG404", "해당 임장이 존재하지 않습니다."),

    LIMJANG_DELETE_NOT_FOUND(HttpStatus.BAD_REQUEST, "LIMJANG4005", "전달된 ID의 값이 DB에 존재하지 않습니다. 전달 값을 다시 확인해주세요."),
    LIMJANG_DELETE_NOT_COMPLETE(HttpStatus.BAD_REQUEST, "LIMJANG4006", "요청한 임장 게시글이 모두 삭제되지 않아 삭제가 취소되었습니다. 다시 시도하거나 백엔드 팀에 문의바랍니다."),
    LIMJANG_UPDATE_PRICETYPE_ERROR(HttpStatus.BAD_REQUEST, "LIMJANG4007", "가격유형 입력값이 잘못되었습니다. 다시 확인해주세요."),
    LIMJANG_REQUEST_SORT_ERROR(HttpStatus.BAD_REQUEST, "LIMJANG4008", "요청한 정렬 방식이 지정되지 않은 값입니다. 다시 확인해주세요."),


    // LimjangPrice Error
    LIMJANGPRICE_NOTFOUND_ERROR(HttpStatus.BAD_REQUEST, "LIMJANGPRICE4000", "해당 임장가격 레코드가 존재하지 않습니다."),


    // scrap
    _SCRAP_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "SCRAP4000", "해당 게시글이 DB에 존재하지 않습니다. 관리자에게 문의 바랍니다."),
    _SCRAP_SCRAP_FAILD(HttpStatus.INTERNAL_SERVER_ERROR, "SCRAP4001", "스크랩 등록 실패. 재시도하거나 관리자에게 문의 바랍니다."),
    _SCRAP_UNSCRAP_FAILD(HttpStatus.INTERNAL_SERVER_ERROR, "SCRAP4002", "스크랩 취소 실패. 재시도하거나 관리자에게 문의 바랍니다."),

    CHECKLIST_TYPE_ERROR(HttpStatus.BAD_REQUEST, "CHECKLIST400", "정해지지 않은 요청값입니다. 다시 확인해주세요."),
    CHECKLIST_NOTFOUND_ERROR(HttpStatus.BAD_REQUEST, "CHECKLIST404", "해당 체크리스트 질문 또는 답변이 존재하지 않습니다."),

    REPORT_NOTFOUND_ERROR(HttpStatus.BAD_REQUEST, "REPORT404", "해당 리포트가 존재하지 않습니다."),

      
    //JWT 토큰 에러
    TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "TOKEN400", "유효하지 않거나 만료된 토큰입니다."),
    TOKEN_EMPTY(HttpStatus.BAD_REQUEST, "TOKEN401", "토큰값이 존재하지 않습니다."),
    REFRESH_TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "TOKEN402", "유효하지 않은 Refresh Token입니다. 다시 로그인하세요."),
    ACCESS_TOKEN_AUTHORIZED(HttpStatus.BAD_REQUEST, "TOKEN403", "유효한 Access Token입니다."),
    REFRESH_TOKEN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "TOKEN404", "유효한 Refresh Token입니다."),

    // Image 에러
    IMAGE_DELETE_NOT_FOUND(HttpStatus.BAD_REQUEST, "IMAGE4000", "전달된 ID의 값이 DB에 존재하지 않습니다. 전달 값을 다시 확인해주세요."),
    IMAGE_DELETE_NOT_COMPLETE(HttpStatus.BAD_REQUEST, "IMAGE4001", "요청한 임장 게시글이 모두 삭제되지 않아 삭제가 취소되었습니다." ),

    //S3 에러
    //FILE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "FILE400", ""),
    S3_NOT_FOUND(HttpStatus.NOT_FOUND, "S34000", "해당 file이 s3에 존재하지 않습니다. 백엔드 팀에 문의바랍니다."),
    S3_DELTE_FAILED(HttpStatus.NOT_FOUND, "S34000", "해당 file이 s3에 존재하지 않습니다. 백엔드 팀에 문의바랍니다."),

    //record 에러
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "RECORD400", "record가 존재하지 않습니다");

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
