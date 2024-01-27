package umc.th.juinjang.service.LimjangService;

import static umc.th.juinjang.utils.LimjangUtil.determineLimjangPrice;

import java.util.List;
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
import umc.th.juinjang.model.dto.limjang.LimjangDeleteRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangDeleteRequestDTO.DeleteDto;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO.PostDto;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.repository.limjang.LimjangPriceRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimjangCommandServiceImpl implements LimjangCommandService {

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;
  private final LimjangPriceRepository limjangPriceRepository;

  @Override
  @Transactional
  public Limjang postLimjang(PostDto request) {

    Limjang limjang = LimjangPostConverter.toLimjang(request);
    // 임장에 회원 정보 넣는 로직
    // 임시로 아무거나 넣게함
    Member findMember = memberRepository.findById(1L)
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
    // 게시글 여러개 삭제 가능
    try {
      limjangRepository.deleteAllByIdInQuery(ids);
    } catch (DataIntegrityViolationException e) {
      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_COMPLETE);
    } catch (EmptyResultDataAccessException e) {
      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_FOUND);
    }


    // limjangToDelete가 비어있으면 해당 ID에 해당하는 데이터가 없음
//    if (limjangToDelete.isEmpty()) {
//      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_FOUND);
//    }
//
//    try {
//      limjangRepository.deleteAllInBatch(limjangToDelete);
//    } catch (DataAccessException e) {
//      throw new LimjangHandler(ErrorStatus.LIMJANG_DELETE_NOT_COMPLETE);
//    }

  }
}
