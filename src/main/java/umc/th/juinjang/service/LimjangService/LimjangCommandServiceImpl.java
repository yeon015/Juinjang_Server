package umc.th.juinjang.service.LimjangService;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.determineLimjangPrice;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.limjang.LimjangPostConverter;
import umc.th.juinjang.converter.limjang.LimjangUpdateConverter;
import umc.th.juinjang.model.dto.limjang.LimjangDeleteRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO.PostDto;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimjangCommandServiceImpl implements LimjangCommandService {

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;
  private final LimjangRetriever limjangRetriever;

  @Override
  @Transactional
  public Limjang postLimjang(PostDto postDto, Member member) {

    Limjang limjang = LimjangPostConverter.toLimjang(postDto);

    Member findMember = memberRepository.findById(member.getMemberId())
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    try {
      limjang.postLimjang(findMember, findLimajngPrice(postDto));
      return limjangRepository.save(limjang);
    } catch (IllegalArgumentException e) {
      log.warn("IllegalArgumentException");
    }
    return null;
  }

  @Override
  @Transactional
  public void deleteLimjangs(LimjangDeleteRequestDTO.DeleteDto deleteIds) {

    System.out.println("임장 선택 삭제 service 입니다");
    List<Long> findIdList = new ArrayList<>();

    for (Long id : deleteIds.getLimjangIdList()){
      findIdList.add(limjangRetriever.findById(id).getLimjangId());
      System.out.println("삭제할 임장 id : : "+id);
    }

    try {
      for (Long id : findIdList){
        System.out.println("try문 안 -- 삭제할 임장 id : : "+id);
        limjangRepository.softDeleteById(id);
      }
    } catch (DataIntegrityViolationException e) {
      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_COMPLETE);
    } catch (EmptyResultDataAccessException e) {
      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_FOUND);
    }
  }

  @Override
  @Transactional
  public void updateLimjang(long memberId, long limjangId, LimjangUpdateRequestDTO.UpdateDto requestUpdateInfo) {

    List<String> newPriceList = requestUpdateInfo.getPriceList();
    // 임장 찾기
    Limjang originalLimjang = limjangRepository.findByIdWithLimjangPrice(memberId, limjangId);
    // 임장 가격
    LimjangPrice findLimjangPrice = originalLimjang.getLimjangPrice();

    // 새 정보
    Limjang newLimjang = LimjangUpdateConverter.toLimjang(requestUpdateInfo);
    LimjangPrice newLimjangPrice = determineLimjangPrice(newPriceList, originalLimjang.getPurpose().getValue(), newLimjang.getPriceType().getValue());

    //업데이트
    originalLimjang.updateLimjang(newLimjang);
    findLimjangPrice.updateLimjangPriceList(newLimjangPrice);
  }

  private LimjangPrice findLimajngPrice (PostDto postDto) {

    List<String> priceList = postDto.getPrice();
    Integer purpose = postDto.getPurposeType();
    Integer priceType = postDto.getPriceType();

    return determineLimjangPrice(priceList, purpose, priceType);
  }
}
