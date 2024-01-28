package umc.th.juinjang.converter.record;

import umc.th.juinjang.model.dto.limjang.LimjangMemoResponseDTO;
import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.model.dto.record.RecordResponseDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Record;

public class LimjangMemoConverter {

    public static LimjangMemoResponseDTO.MemoDto toDto(Limjang limjang){
        return LimjangMemoResponseDTO.MemoDto.builder()
                .memo(limjang.getMemo())
                .limjangId(limjang.getLimjangId())
                .createdAt(limjang.getCreatedAt())
                .updatedAt(limjang.getUpdatedAt())
                .build();
    }
}
