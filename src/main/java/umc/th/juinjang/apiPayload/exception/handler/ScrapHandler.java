package umc.th.juinjang.apiPayload.exception.handler;

import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.exception.GeneralException;

public class ScrapHandler extends GeneralException {
  public ScrapHandler(BaseErrorCode code) {
    super(code);
  }
}
