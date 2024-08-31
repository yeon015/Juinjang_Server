package umc.th.juinjang.external.discord;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import umc.th.juinjang.external.discord.dto.DiscordAlert;

@FeignClient(name = "${discord.name}", url = "${discord.webhook-url}")
public interface DiscordFeignClient {
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  void sendMessage(@RequestBody DiscordAlert discordMessage);
}