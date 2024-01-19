package umc.th.juinjang.service.LimjangService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.limjang.LimjangMainListConverter;
import umc.th.juinjang.converter.limjang.LimjangTotalListConverter;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO.RecentUpdatedDto;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO.TotalListDto;
import umc.th.juinjang.model.entity.Image;
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
  @Transactional(readOnly = true)
  public LimjangTotalListResponseDTO.TotalListDto getLimjangTotalList() {

    // 멤버 찾기(임시구현)
    Member findMember = memberRepository.findById(1L)
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    // 멤버가 가지고있는 모든 글
    List<Limjang> findAllLimjangList = limjangRepository.findLimjangByMemberId(findMember);

    // 스크랩 된 게시글
    List<LimjangTotalListResponseDTO.ListDto> scrapdList = findAllLimjangList.stream()
        .filter(limjang -> limjang.getScrap() != null)
        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId(),3))
            .toList();

    // 스크랩 안 된 리스트
    List<LimjangTotalListResponseDTO.ListDto> unScrapdList = findAllLimjangList.stream()
        .filter(limjang -> limjang.getScrap() == null)
        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId(), 1))
        .toList();

    return LimjangTotalListConverter.toLimjangTotalList(scrapdList, unScrapdList);

  }

  @Override
  @Transactional(readOnly = true)
  public List<LimjangMainViewListResponsetDTO.ListDto> getLimjangMainList() {
    // 멤버 찾기(임시구현)
    Member findMember = memberRepository.findById(1L)
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    // 멤버가 가지고있는 모든 글
    List<Limjang> findAllLimjangList = limjangRepository.findLimjangByMemberId(findMember);

    // 멤버가 가지고있는 모든 글
    return findAllLimjangList.stream()
        .sorted(Comparator.comparing(Limjang::getUpdatedAt).reversed()) // updatedAt 기준으로 내림차순 정렬
        .limit(5) // 최대 5개까지만 선택
        .map(limjang -> LimjangMainListConverter.toLimjangList(limjang, limjang.getPriceId()))
        .toList();
  }
}
