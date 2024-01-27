package umc.th.juinjang.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.model.entity.common.BaseEntity;
import umc.th.juinjang.model.entity.enums.MemberProvider;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

  @Id
  @Column(name="member_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MemberProvider provider;

  @Column(nullable = false)
  private String refreshToken;

  @Column(nullable = false)
  private LocalDateTime refreshTokenExpiresAt;

  @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL)
  private List<Limjang> limjangList = new ArrayList<>();

  // refreshToken 재발급
  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    this.refreshTokenExpiresAt = LocalDateTime.now().plusDays(7);
  }

  // 로그아웃 시 토큰 만료
  public void refreshTokenExpires() {
    this.refreshToken = "";
    this.refreshTokenExpiresAt = LocalDateTime.now();
  }

}