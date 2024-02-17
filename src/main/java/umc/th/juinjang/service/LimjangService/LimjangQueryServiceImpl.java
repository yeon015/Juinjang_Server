package umc.th.juinjang.service.LimjangService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.limjang.LimjangDetailConverter;
import umc.th.juinjang.converter.limjang.LimjangMainListConverter;
import umc.th.juinjang.converter.limjang.LimjangTotalListConverter;
import umc.th.juinjang.model.dto.limjang.LimjangDetailResponseDTO.DetailDto;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.Report;
import umc.th.juinjang.model.entity.enums.LimjangSort;
import umc.th.juinjang.repository.checklist.ReportRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimjangQueryServiceImpl implements LimjangQueryService{

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;
  private final ReportRepository reportRepository;

  @Override
  @Transactional(readOnly = true)
  public LimjangTotalListResponseDTO.TotalListDto getLimjangTotalList(Member member, String sort) {

    System.out.println("임장 전체 조회 API Service");

    // 멤버 찾기
    Member findMember = memberRepository.findById(member.getMemberId()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    System.out.println("찾은 멤버의 id: "+findMember.getMemberId());

    LimjangSort requestSort;
    try{
      requestSort = LimjangSort.valueOf(sort);
    }catch (IllegalArgumentException e){
      System.out.println("지정되지 않은 정렬 유형.");
      throw new LimjangHandler(ErrorStatus.LIMJANG_REQUEST_SORT_ERROR);
    }

    // 멤버가 가지고있는 모든 글
    List<Limjang> findLimjangList = limjangRepository.findLimjangByMemberId(findMember).stream().peek(
          limjang -> {
            Report report = reportRepository.findByLimjangId(limjang).orElse(null);
            limjang.saveReport(report);
          }
      ).toList();

    List<Limjang> sortedLimjangList = new ArrayList<>();
    switch (requestSort) {
      case UPDATED:
        sortedLimjangList = findLimjangList.stream()
            .sorted(Comparator.comparing(Limjang::getUpdatedAt).reversed()).toList();
        break;
      case CREATED:
        sortedLimjangList = findLimjangList.stream()
            .sorted(Comparator.comparing(Limjang::getCreatedAt).reversed()).toList();
        break;
      case STAR :
        sortedLimjangList = findLimjangList.stream()
            .sorted(Comparator.comparing(limjang -> limjang.getReport() != null ? limjang.getReport().getTotalRate() : null,
                Comparator.nullsLast(Comparator.reverseOrder())))
            .toList();
        break;
    }

    return LimjangTotalListConverter.toLimjangTotalList(sortedLimjangList);
  }

  @Override
  @Transactional(readOnly = true)
  public List<LimjangMainViewListResponsetDTO.ListDto> getLimjangMainList(Member member) {
    System.out.println("임장 메인화면 조회 API Service, 첫번째 줄");
    // 멤버 찾기
    Member findMember = memberRepository.findById(member.getMemberId())
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    // 임장 찾는다
    System.out.println("임장 메인화면 조회 API Service, 멤버 찾음");
    return limjangRepository.findTop5ByMemberIdOrderByUpdatedAtDesc(findMember)
        .stream()
        .peek(limjang -> {
          Report report = reportRepository.findByLimjangId(limjang).orElse(null);
          limjang.saveReport(report);
        }).map(limjang -> LimjangMainListConverter.toLimjangList(limjang, limjang.getPriceId())).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public LimjangTotalListResponseDTO.TotalListDto getLimjangSearchList(String keyword) {

    Member findMember = memberRepository.findById(1L)
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    List<Limjang> findLimjangListByKeyword = limjangRepository.searchLimjangs(findMember, keyword).stream().peek(
        limjang -> {
          Report report = reportRepository.findByLimjangId(limjang).orElse(null);
          limjang.saveReport(report);
        }
    ).toList();

    return LimjangTotalListConverter.toLimjangTotalList(findLimjangListByKeyword);
  }

  @Override
  @Transactional(readOnly = true)
  public DetailDto getLimjangDetail(Long limjangId) {

    Limjang findLimjang = limjangRepository.findById(limjangId)
        .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

    return LimjangDetailConverter.toDetail(findLimjang, findLimjang.getPriceId());
  }
}
