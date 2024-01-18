package umc.th.juinjang.service.LimjangService;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.converter.limjang.LimjangPostConverter;
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
    Limjang limjang = LimjangPostConverter.toEntity(request);

    // 임장에 회원 정보 넣는 로직
    // 임시로 아무거나 넣게함
    Optional<Member> findMember = memberRepository.findById(1L);

    // 넣기.. 멤버 id


    // 임장 가격 테이블에 가격 저장
    try {
      LimjangPrice limjangPrice = determineLimjangPrice(request);
      limjangPriceRepository.save(limjangPrice);
      limjang.postLimjang(findMember.orElseThrow(), limjangPrice);
      limjangRepository.save(limjang);
    } catch (NullPointerException e) {
      log.warn("price가 NULL");
    }


    return null;
  }


  public LimjangPrice determineLimjangPrice(PostDto request){
    // 만약에 배열 길이가 1 또는 2가 아니면 오류임.


    if (request.getPurpose() == 0){ // 부동산 투자 목적 -> 실거래가
      return LimjangPrice.builder().marketPrice(request.getPrice().get(0)).build();
    } else if (request.getPurpose() == 1){ // 직접 거래 목적
      switch (request.getPriceType()){
        case 0 : // 매매
          return LimjangPrice.builder().sellingPrice(request.getPrice().get(0)).build();
        case 1 :// 전세
          return LimjangPrice.builder().pullRent(request.getPrice().get(0)).build();
        case 2 : // 월세 : 0, 보증금 : 1 이 경우 배열 길이는 무조건 2여야만 함.
          return LimjangPrice.builder().depositPrice(request.getPrice().get(0))
              .monthlyRent(request.getPrice().get(1)).build();
      }
    }
    return null;
  }

//  Member newMember = MemberConverter.toMember(request);
//  List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
//      .map(category -> {
//        return foodCategoryRepository.findById(category).orElseThrow(() -> new FoodCategoryHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND));
//      }).collect(Collectors.toList());
//
//  List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);
//
//        memberPreferList.forEach(memberPrefer -> {memberPrefer.setMember(newMember);});
//
//        return memberRepository.save(newMember);
}
