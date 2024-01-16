package umc.th.juinjang.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long memberId;
//
//  private String email;
//
//  private String nickname;
//
//  Enum
////  private String provider;
//
//  private


}