package umc.th.juinjang.converter.limjang;

import umc.th.juinjang.model.dto.limjang.LimjangPostRequest;
import umc.th.juinjang.model.dto.limjang.LimjangPostResponse;
import umc.th.juinjang.model.dto.limjang.LimjangPostResponse.PostDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPropertyType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

public class LimjangPostConverter {

  // 요청
  public static Limjang toEntity(LimjangPostRequest.PostDto postDto) {

    return Limjang.builder()
        .purpose(LimjangPurpose.find(postDto.getPurpose()))
        .propertyType(LimjangPropertyType.find(postDto.getPropertyType()))
        .priceType(LimjangPriceType.find(postDto.getPriceType()))
        .address(postDto.getAddress())
        .addressDetail(postDto.getAddressDetail())
        .nickname(postDto.getNickname())
        .build();
  }

  // 응답
  public static LimjangPostResponse.PostDTO toPostDTO(Long limjangId){
    return PostDTO.builder()
        .limjangId(limjangId)
        .build();
  }

  public static LimjangPostResponse.PostExceptionDTO toPostExceptionDTO(Integer flag){
    return LimjangPostResponse.PostExceptionDTO.builder()
        .flag(flag)
        .build();
  }
}
