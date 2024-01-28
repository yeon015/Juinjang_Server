package umc.th.juinjang.apiPayload.exception.handler;

import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.exception.GeneralException;

public class S3Handler extends GeneralException {
  public S3Handler(BaseErrorCode code) {
    super(code);
  }
}
