package umc.th.juinjang.apiPayload;

import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.exception.GeneralException;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseErrorCode code) {
        super(code);
    }
}
