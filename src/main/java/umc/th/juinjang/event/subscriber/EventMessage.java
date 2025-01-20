package umc.th.juinjang.event.subscriber;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EventMessage {
  SIGN_UP_MESSAGE("주인장에 %s %d번째 유저 < %s >님이 생겼어요!");

  private final String message;
}
