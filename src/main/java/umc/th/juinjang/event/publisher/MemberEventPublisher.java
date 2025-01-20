package umc.th.juinjang.event.publisher;

import umc.th.juinjang.model.entity.Member;

public interface MemberEventPublisher {
  void publishSignUpEvent(Member member);
}
