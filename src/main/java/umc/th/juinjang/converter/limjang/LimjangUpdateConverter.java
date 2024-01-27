package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.utils.LimjangUtil.checkExpectedSize;
import static umc.th.juinjang.utils.LimjangUtil.makePriceList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.utils.LimjangUtil;
import umc.th.juinjang.validation.annotation.VaildPriceListSize;

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
