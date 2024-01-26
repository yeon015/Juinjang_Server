package umc.th.juinjang.converter.record;

import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Record;
import umc.th.juinjang.repository.limjang.LimjangRepository;

public class RecordConverter {

    static LimjangRepository limjangRepository ;
    public static Record toEntity(RecordRequestDTO.RecordDto recordDto, String fileUrl){
        return Record.builder()
                .recordName(recordDto.getRecordName())
                .recordUrl(fileUrl)
                .recordScript(recordDto.getRecordScript())
                .recordTime(recordDto.getRecordTime())
                .limjangId(convertToLimjangEntity(recordDto.getLimjangId()))
                .build();
    }

    private static Limjang convertToLimjangEntity(Long limjangId) {
        if(limjangRepository.findById(limjangId).isPresent())
            return limjangRepository.findById(limjangId).get();

        throw new ExceptionHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR);
    }
}
