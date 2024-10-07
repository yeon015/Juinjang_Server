package umc.th.juinjang.model.dto.limjang.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPropertyType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

public record LimjangPostRequest(
    @NotNull
    Integer purposeType,
    Integer propertyType,
    Integer priceType,
    @Size(min = 1, max = 2)
    List<String> price,
    @NotBlank
    String address,
    String addressDetail,
    @NotBlank
    String nickname
) {
    public Limjang toEntity() {
        return Limjang.builder()
            .purpose(LimjangPurpose.find(purposeType))
            .propertyType(LimjangPropertyType.find(propertyType))
            .priceType(LimjangPriceType.find(priceType))
            .address(address)
            .addressDetail(addressDetail)
            .nickname(nickname)
            .build();
    }
}