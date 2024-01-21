package umc.th.juinjang.apiPayload.exception.handler;

import umc.th.juinjang.apiPayload.code.BaseErrorCode;
import umc.th.juinjang.apiPayload.exception.GeneralException;
import umc.th.juinjang.repository.limjang.MemberRepository;

public class MemberHandler extends GeneralException {
  public MemberHandler(BaseErrorCode code) {
    super(code);
  }
}
