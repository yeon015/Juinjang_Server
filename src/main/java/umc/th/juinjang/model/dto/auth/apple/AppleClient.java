package umc.th.juinjang.model.dto.auth.apple;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth") // configuration 속성 제거함

public interface AppleClient {
    @GetMapping(value = "/keys")
    ApplePublicKeyResponse getAppleAuthPublicKey();

    @PostMapping(value = "/token", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    AppleTokenResponse getAppleTokens(@RequestBody AppleTokenRequest request);


    @PostMapping(value = "/revoke", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    void revoke(@RequestPart(value = "token") String token,
                @RequestPart(value = "client_id") String client_id,
                @RequestPart(value = "client_secret") String client_secret,
                @RequestPart(value = "token_type_hint") String token_type_hint);

}
