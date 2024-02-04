package umc.th.juinjang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.th.juinjang.model.entity.Member;


@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal UserDetails member) {
//        System.out.println("authentication test" + member.getUsername());
        log.info("============="+member.getPassword());

        return "Hello, World!";
    }
}
