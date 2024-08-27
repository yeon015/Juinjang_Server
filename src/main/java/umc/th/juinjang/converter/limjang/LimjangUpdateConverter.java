package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.checkExpectedSize;

import java.util.List;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.model.dto.limjang.request.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;

public class LimjangUpdateConverter {

  public static Limjang toLimjang(LimjangUpdateRequestDTO.UpdateDto updateDto){

    List<String> priceList = updateDto.getPriceList();
    Integer priceType = updateDto.getPriceType();

    checkExpectedSize(priceType, priceList.size(), ErrorStatus.LIMJANG_UPDATE_PRICETYPE_ERROR);

    return Limjang.builder()
        .address(updateDto.getAddress())
        .addressDetail(updateDto.getAddressDetail())
        .nickname(updateDto.getNickname())
        .priceType(LimjangPriceType.find(priceType))
        .build();
  }

}
