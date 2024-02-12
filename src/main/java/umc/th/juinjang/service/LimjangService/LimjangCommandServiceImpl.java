package umc.th.juinjang.service.LimjangService;

import static umc.th.juinjang.utils.LimjangUtil.determineLimjangPrice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
import umc.th.juinjang.model.dto.limjang.LimjangDeleteRequestDTO.DeleteDto;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO.PostDto;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.repository.limjang.LimjangPriceRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;
import umc.th.juinjang.utils.LimjangUtil;
import umc.th.juinjang.validation.annotation.VaildPriceListSize;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimjangCommandServiceImpl implements LimjangCommandService {

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;
  private final LimjangPriceRepository limjangPriceRepository;

  @Override
  @Transactional
  public Limjang postLimjang(PostDto request, Member member) {

    Limjang limjang = LimjangPostConverter.toLimjang(request);

    Member findMember = memberRepository.findById(member.getMemberId())
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

    // 임장 가격 테이블에 가격 저장 후 입장에 member, limjangprice추가
    // 임장 테이블에 저장.
    try {
      List<String> priceList = request.getPrice();
      Integer purpose = request.getPurposeType();
      Integer priceType = request.getPriceType();

      LimjangPrice limjangPrice = determineLimjangPrice(priceList, purpose, priceType);
      limjangPriceRepository.save(limjangPrice);

      limjang.postLimjang(findMember, limjangPrice);

      return limjangRepository.save(limjang);
    } catch (IllegalArgumentException e) {
      log.warn("IllegalArgumentException");
    }
    return null;
  }

  @Override
  @Transactional
  public void deleteLimjangs(List<Long> ids) {
    for (Long id : ids){
      System.out.println("삭제할 임장 id : : "+id);
    }
    System.out.println("임장 선택 삭제 service 입니다");

    // 게시글 여러개 삭제 가능
    try {
      limjangRepository.deleteAllById(ids);
    } catch (DataIntegrityViolationException e) {
      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_COMPLETE);
    } catch (EmptyResultDataAccessException e) {
      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_FOUND);
    }

  }

  @Override
  @Transactional
  public void updateLimjang(LimjangUpdateRequestDTO.UpdateDto requestUpdateInfo) {

    List<String> newPriceList = requestUpdateInfo.getPriceList();

    // 임장 찾기
    Limjang originalLimjang = limjangRepository.findById(requestUpdateInfo.getLimjangId())
        .orElseThrow(()-> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));
    // 임장 가격 찾기
    LimjangPrice originalLimjangPrice = limjangPriceRepository.findById(originalLimjang.getPriceId().getPriceId())
        .orElseThrow(()-> new LimjangHandler(ErrorStatus.LIMJANGPRICE_NOTFOUND_ERROR));

    // 새 정보
    Limjang newLimjang = LimjangUpdateConverter.toLimjang(requestUpdateInfo);
    LimjangPrice newLimjangPrice = determineLimjangPrice(newPriceList, originalLimjang.getPurpose().getValue(), newLimjang.getPriceType().getValue());

    //업데이트
    originalLimjang.updateLimjang(newLimjang);
    originalLimjangPrice.updateLimjangPriceList(newLimjangPrice);
  }

}
