package umc.th.juinjang.external.discord;

import feign.FeignException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.code.status.SuccessStatus;
import umc.th.juinjang.external.discord.dto.DiscordAlert;

@RequiredArgsConstructor
@Component
@Slf4j
public class DiscordAlertProvider {

  private final DiscordFeignClient discordFeignClient;
  private final Environment environment;

  public void sendAlert(SuccessStatus successStatus) {
    if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
      sendAlertToDiscord(DiscordAlert.createAlert(successStatus.getMessage()));
    }
  }

  private void sendAlertToDiscord(DiscordAlert discordAlert) {
    try {
      discordFeignClient.sendAlert(discordAlert);
    } catch (FeignException e) {
      log.info(ErrorStatus.DISCORD_ALERT_ERROR.getMessage()+ " " +e.getMessage());
    }
  }
}