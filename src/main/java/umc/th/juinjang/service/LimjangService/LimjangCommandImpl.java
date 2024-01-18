package umc.th.juinjang.service.LimjangService;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class LimjangCommandImpl implements LimjangCommandService {

  private final LimjangRepository limjangRepository;
  private final MemberRepository memberRepository;
  private final LimjangPriceRepository limjangPriceRepository;

  @Override
  @Transactional
  public Limjang postLimjang(PostDto request) {
//    Limjang limjang = LimjangPostConverter.toEntity(request);
//
//    // 임장에 회원 정보 넣는 로직
//    // 임시로 아무거나 넣게함
//    Optional<Member> findMember = memberRepository.findById(1L);
//
//    // 임장 가격 테이블에 가격 저장
//
//    // 갸격(매매가, 전세가, {보증금, 월세} 중 1개)
//    switch (request.getPriceType()){
//      case 0 :
//        //
//      case 1 :
//      case 2 :
//
//    }
////    limjang.postLimjang(member, );
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
