package umc.th.juinjang.service.LimjangService;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.determineLimjangPrice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.limjang.LimjangPostRequestConverter;
import umc.th.juinjang.converter.limjang.LimjangUpdateConverter;
import umc.th.juinjang.model.dto.limjang.request.LimjangDeleteRequestDTO;
import umc.th.juinjang.model.dto.limjang.request.LimjangDeleteRequestDTO.DeleteDto;
import umc.th.juinjang.model.dto.limjang.request.LimjangPostRequest;
import umc.th.juinjang.model.dto.limjang.request.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.dto.limjang.request.LimjangUpdateRequestDTO.UpdateDto;
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
  public Limjang postLimjang(LimjangPostRequest postDto, Member member) {
    Limjang limjang = LimjangPostRequestConverter.toLimjang(postDto);
    limjang.postLimjang(findMemberById(member), findLimajngPrice(postDto));
    return limjangRepository.save(limjang);
  }

  private Member findMemberById(Member member) {
    return memberRepository.findById(member.getMemberId())
        .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
  }

  @Override
  @Transactional
  public void deleteLimjangs(LimjangDeleteRequestDTO.DeleteDto deleteIds) {
    checkLimjangExistence(deleteIds);
    deleteIds.getLimjangIdList().forEach(limjangRepository::softDeleteById);
  }

  private void checkLimjangExistence(DeleteDto deleteIds) {
    for (Long id : deleteIds.getLimjangIdList()){
      limjangRetriever.findById(id);
    }
  }

  @Override
  @Transactional
  public void updateLimjang(long memberId, long limjangId, LimjangUpdateRequestDTO.
    UpdateDto requestUpdateInfo) {

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

  private LimjangPrice findLimajngPrice (LimjangPostRequest postDto) {

    List<String> priceList = postDto.price();
    Integer purpose = postDto.purposeType();
    Integer priceType = postDto.priceType();

    return determineLimjangPrice(priceList, purpose, priceType);
  }
}