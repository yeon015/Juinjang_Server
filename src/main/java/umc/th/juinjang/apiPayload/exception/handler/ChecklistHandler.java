package umc.th.juinjang.apiPayload.exception.handler;

import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.exception.GeneralException;

public class ChecklistHandler extends GeneralException {
    public ChecklistHandler(BaseErrorCode code) {
      super(code);
    }
}
