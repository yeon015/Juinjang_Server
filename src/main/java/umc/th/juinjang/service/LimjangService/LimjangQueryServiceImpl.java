package umc.th.juinjang.service.LimjangService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.limjang.LimjangDetailConverter;
import umc.th.juinjang.converter.limjang.LimjangTotalListConverter;
import umc.th.juinjang.model.dto.limjang.response.LimjangDetailResponseDTO.DetailDto;
import umc.th.juinjang.model.dto.limjang.response.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsMainGetResponse;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.dto.limjang.enums.LimjangSortOptions;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimjangQueryServiceImpl implements LimjangQueryService{

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;

  @Override
  @Transactional(readOnly = true)
  public LimjangsGetResponse getLimjangTotalList(Member member, LimjangSortOptions sort) {
    return LimjangsGetResponse.of(limjangRepository.findAllByMemberAndOrderByParam(findMemberById(member), sort));
  }

  @Override
  @Transactional(readOnly = true)
  public LimjangsMainGetResponse getLimjangsMain(final Member member) {
    return LimjangsMainGetResponse.of(limjangRepository.findMainScreenContentsLimjang(findMemberById(member)));
  }

  @Override
  @Transactional(readOnly = true)
  public LimjangTotalListResponseDTO.TotalListDto getLimjangSearchList(Member member, String keyword) {

    Member findMember = memberRepository.findById(member.getMemberId())
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    List<Limjang> findLimjangListByKeyword = limjangRepository.searchLimjangs(findMember, keyword).stream().toList();

    return LimjangTotalListConverter.toLimjangTotalList(findLimjangListByKeyword);
  }

  @Override
  @Transactional(readOnly = true)
  public DetailDto getLimjangDetail(Long limjangId) {

    Limjang findLimjang = limjangRepository.findById(limjangId)
        .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

    return LimjangDetailConverter.toDetail(findLimjang, findLimjang.getLimjangPrice());
  }

  private Member findMemberById(Member member) {
    return memberRepository.findById(member.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
  }
}
