package umc.th.juinjang.converter.record;

import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.model.dto.record.RecordResponseDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Record;

public class RecordConverter {

    public static Record toEntity(RecordRequestDTO.RecordDto recordDto, String fileUrl, Limjang limjang){
        return Record.builder()
                .recordName(recordDto.getRecordName())
                .recordUrl(fileUrl)
                .recordScript(recordDto.getRecordScript())
                .recordTime(recordDto.getRecordTime())
                .limjangId(limjang)
                .build();
    }

    public static RecordResponseDTO.RecordDto toDto(Record record, Long limjangId){
        return RecordResponseDTO.RecordDto.builder()
                .recordName(record.getRecordName())
                .recordScript(record.getRecordScript())
                .recordTime(record.getRecordTime())
                .limjangId(limjangId)
                .build();
    }

}
