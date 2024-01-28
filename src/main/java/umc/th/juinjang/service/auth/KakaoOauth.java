package umc.th.juinjang.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import umc.th.juinjang.model.dto.auth.kakao.KakaoOAuthToken;
import umc.th.juinjang.model.dto.auth.kakao.KakaoUser;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoOauth {

    @Value("${security.oauth2.client.provider.kakao.authorization-uri}")
    private String KAKAO_LOGIN_URL;

    @Value("${security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;

    @Value("${security.oauth2.client.registration.kakao.client-secret}")
    private String KAKAO_SNS_CLIENT_SECRET;

    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_SNS_CALLBACK_URL;

    @Value("${security.oauth2.client.provider.kakao.token-uri}")
    private String KAKAO_SNS_TOKEN_BASE_URL;

    @Value("${security.oauth2.client.provider.kakao.user-info-uri}")
    private String KAKAO_SNS_USERINFO_URL;

    private final ObjectMapper objectMapper;

    // 사용자 로그인 페이지 제공 단계 - url 생성
    public String getOauthRedirectURL() {
        String reqUrl = KAKAO_LOGIN_URL + "?client_id=" + KAKAO_SNS_CLIENT_ID + "&redirect_uri=" + KAKAO_SNS_CALLBACK_URL
                + "&response_type=code";
        return reqUrl;
    }

    // 카카오로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
    public ResponseEntity<String> requestAccessToken(String code) {

        // RestTemplate : 스프링에서 제공하는 http 통신에 유용하게 쓸 수 있는 템플릿
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", KAKAO_SNS_CLIENT_ID);
        params.add("client_secret", KAKAO_SNS_CLIENT_SECRET);
        params.add("redirect_uri", KAKAO_SNS_CALLBACK_URL);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        // RestTemplate 주요 메서드 postForEntity : POST 요청을 보내고 결과로 ResponseEntity 로 반환
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(KAKAO_SNS_TOKEN_BASE_URL, request, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println(responseEntity.getBody());
            return responseEntity;
        }
        System.out.println("null...");
        return null;
    }

    // 응답 객체가 JSON 형식으로 되어 있으므로, 이를 deserialization 해서 자바 객체에 담음
    public KakaoOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        log.info("response.getBody() = " + response.getBody());
        KakaoOAuthToken kakaoOAuthToken = objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
        return kakaoOAuthToken;
    }

    // 액세스 토큰을 다시 카카오로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아옴
    public ResponseEntity<String> requestUserInfo(KakaoOAuthToken oAuthToken) {

        RestTemplate restTemplate = new RestTemplate();
        // header 에 accessToken 을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpEntity 를 하나 생성해, 헤더를 담아서 restTemplate 으로 카카오와 통신하게 된다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_SNS_USERINFO_URL, HttpMethod.GET,request,String.class);
        log.info("response.getHeaders() = " + response.getHeaders());
        log.info("responseEntity.getStatusCode()"+ response.getStatusCode());
        log.info("response.getBody() = " + response.getBody());
        return response;
    }

    // 다시 JSON 형식의 응답 객체를 deserialization 해서 자바 객체에 담음
    public KakaoUser getUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException {
        log.info("response.getBody() = "+userInfoResponse.getBody());
        KakaoUser kakaoUser = objectMapper.readValue(userInfoResponse.getBody(), KakaoUser.class);
        System.out.println(kakaoUser.getKakaoAccount().getEmail());
        System.out.println(kakaoUser.getId());
        return kakaoUser;
    }
}
