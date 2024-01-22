package umc.th.juinjang.apiPayload.exception.handler;

import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.exception.GeneralException;

public class LimjangHandler extends GeneralException {
    public LimjangHandler(BaseErrorCode code) {
      super(code);
    }
}
