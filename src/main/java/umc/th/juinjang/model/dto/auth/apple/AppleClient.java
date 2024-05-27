package umc.th.juinjang.model.dto.auth.apple;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import umc.th.juinjang.JuinjangApplication;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth") // configuration 속성 제거

public interface AppleClient {
    @GetMapping(value = "/keys")
    ApplePublicKeyResponse getAppleAuthPublicKey();


}
