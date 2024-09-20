package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.checkExpectedSize;

import java.util.List;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.model.dto.limjang.request.LimjangPostRequest;
import umc.th.juinjang.model.dto.limjang.response.LimjangPostResponseDTO;
import umc.th.juinjang.model.dto.limjang.response.LimjangPostResponseDTO.PostDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPropertyType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

public class LimjangPostRequestConverter {

  // 요청
  public static Limjang toLimjang(LimjangPostRequest postDto) {
    List<String> priceList = postDto.price();

    Integer priceType = postDto.priceType();

    checkExpectedSize(priceType, priceList.size(), ErrorStatus.LIMJANG_POST_PRICE_ERROR);

      return Limjang.builder()
        .purpose(LimjangPurpose.find(postDto.purposeType()))
        .propertyType(LimjangPropertyType.find(postDto.propertyType()))
        .priceType(LimjangPriceType.find(priceType))
        .address(postDto.address())
        .addressDetail(postDto.addressDetail())
        .nickname(postDto.nickname())
        .build();
  }

  // 응답
  public static LimjangPostResponseDTO.PostDTO toPostDTO(Limjang limjang){
    return PostDTO.builder()
        .limjangId(limjang.getLimjangId())
        .createdAt(limjang.getCreatedAt())
        .build();
  }

  public static LimjangPostResponseDTO.PostExceptionDTO toPostExceptionDTO(Integer flag){
    return LimjangPostResponseDTO.PostExceptionDTO.builder()
        .flag(flag)
        .build();
  }
}
