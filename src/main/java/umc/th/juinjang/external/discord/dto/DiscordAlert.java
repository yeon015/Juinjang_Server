package umc.th.juinjang.external.discord.dto;

public record DiscordAlert(String content) {
  public static DiscordAlert createAlert(String content) {
    return new DiscordAlert(content);
  }
}