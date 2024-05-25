package umc.th.juinjang.repository.limjang.dto;

import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Report;

public record LimjangMainListDBResponsetDto(
    Long limjangId,
    Image image,
    String nickname,
    LimjangPrice limjangPrice,
    Report report,
    String address
    //      "limjangId": 8,
//          "image": null,
//          "nickname": "string",
//          "price": "20000",
//          "totalAverage": null,
//          "address": "string"

) {

}
