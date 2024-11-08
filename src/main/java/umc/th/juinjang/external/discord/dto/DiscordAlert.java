package umc.th.juinjang.external.discord.dto;

import umc.th.juinjang.model.entity.enums.MemberProvider;

public record DiscordAlert(String content) {
  public static DiscordAlert createAlert(Long id, MemberProvider memberProvider, String nickname) {
    return new DiscordAlert("주인장에 " +memberProvider+" "+id+"번째 유저 < "+nickname+" >님이 생겼어요!");
  }
}