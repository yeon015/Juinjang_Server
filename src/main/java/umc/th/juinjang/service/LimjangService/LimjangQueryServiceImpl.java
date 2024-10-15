package umc.th.juinjang.service.LimjangService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.limjang.LimjangDetailConverter;
import umc.th.juinjang.model.dto.limjang.response.LimjangDetailResponseDTO.DetailDto;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetByKeywordResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsMainGetResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsMainGetVersion2Response;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.dto.limjang.enums.LimjangSortOptions;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;
import umc.th.juinjang.repository.limjang.ScrapRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimjangQueryServiceImpl implements LimjangQueryService{

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;
  private final ScrapRepository scrapRepository;

  @Override
  @Transactional(readOnly = true)
  public LimjangsGetResponse getLimjangTotalList(Member member, LimjangSortOptions sort) {
    checkMemberExist(member);
    List<Limjang> limjangList = limjangRepository.findAllByMemberAndDeletedIsFalseOrderByParam(member, sort);
    return LimjangsGetResponse.of(limjangList, mapLimjangToScrapStatus(limjangList));
  }

  @Override
  @Transactional(readOnly = true)
  public LimjangsMainGetResponse getLimjangsMain(final Member member) {
    return LimjangsMainGetResponse.of(limjangRepository.findAllByMemberAndDeletedIsFalseWithReportAndLimjangPriceOrderByUpdateAtLimit5(checkMemberExist(member)));
  }

  @Override
  @Transactional(readOnly = true)
  public LimjangsGetByKeywordResponse getLimjangSearchList(Member member, String keyword) {
    checkMemberExist(member);
    List<Limjang> limjangList = limjangRepository.searchLimjangsWhereDeletedIsFalse(member, keyword);
    return LimjangsGetByKeywordResponse.of(limjangList, mapLimjangToScrapStatus(limjangList));
  }

  @Override
  @Transactional(readOnly = true)
  public DetailDto getLimjangDetail(Long limjangId) {
    Limjang findLimjang = limjangRepository.findById(limjangId)
        .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));
    return LimjangDetailConverter.toDetail(findLimjang, findLimjang.getLimjangPrice());
  }

  @Override
  @Transactional(readOnly = true)
  public LimjangsMainGetVersion2Response getLimjangsMainVersion2(Member member) {
    return LimjangsMainGetVersion2Response.of(limjangRepository.findAllByMemberAndDeletedIsFalseWithReportAndLimjangPriceOrderByUpdateAtLimit5(checkMemberExist(member)));
  }

  private Member checkMemberExist(Member member) {
    return memberRepository.findById(member.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
  }

  private Map<Long, Boolean> mapLimjangToScrapStatus(List<Limjang> limjangList) {
    Set<Long> limjangIdsInScrap = getLimjangIdsInScrap(limjangList);
    return limjangList.stream().collect(Collectors.toMap(
        Limjang::getLimjangId,
        it -> limjangIdsInScrap.contains(it.getLimjangId())
    ));
  }

  private Set<Long> getLimjangIdsInScrap(List<Limjang> limjangList) {
    return new HashSet<>(scrapRepository.findAllByLimjangIdIn(limjangList).stream().map(it -> it.getLimjangId().getLimjangId()).toList());
  }
}
