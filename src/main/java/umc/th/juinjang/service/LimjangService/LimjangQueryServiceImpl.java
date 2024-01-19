package umc.th.juinjang.service.LimjangService;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.limjang.LimjangTotalListConverter;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO.TotalListDto;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.repository.limjang.LimjangPriceRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;
import umc.th.juinjang.repository.limjang.ScrapRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimjangQueryServiceImpl implements LimjangQueryService{

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;
  private final LimjangPriceRepository limjangPriceRepository;
  private final ScrapRepository scrapRepository;

  @Override
  public LimjangTotalListResponseDTO.TotalListDto getLimjangTotalList() {

    // 멤버 찾기(임시구현)
    Member findMember = memberRepository.findById(1L)
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    // 멤버가 가지고있는 모든 글
    List<Limjang> findAllLimjangList = limjangRepository.findLimjangByMemberId(findMember);

    // 스크랩 된 게시글
    List<LimjangTotalListResponseDTO.ListDto> scrapdList = findAllLimjangList.stream()
        .filter(limjang -> limjang.getScrap() != null)
        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId()))
            .toList();

    // 스크랩 안 된 리스트
    List<LimjangTotalListResponseDTO.ListDto> unScrapdList = findAllLimjangList.stream()
        .filter(limjang -> limjang.getScrap() == null)
        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId()))
        .toList();

    return LimjangTotalListConverter.toLimjangTotalList(scrapdList, unScrapdList);

  }
}
