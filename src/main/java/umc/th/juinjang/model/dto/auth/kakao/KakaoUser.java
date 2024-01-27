package umc.th.juinjang.model.dto.auth.kakao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KakaoUser {
    // 다시 JSON 형식의 응답 객체를 deserialization 해서 자바 객체에 담음
    // -> 여기서 자바 객체에 해당하는 클래스
    private Long id;
    private KakaoAccount kakaoAccount;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public class KakaoAccount {
        private String email;
        private Boolean profile_nickname_needs_agreement;
        private Profile profile;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public class Profile {
            private String nickname;
        }

    }

}