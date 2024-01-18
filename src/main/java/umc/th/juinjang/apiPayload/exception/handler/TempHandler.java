package umc.th.juinjang.apiPayload.exception.handler;


import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
