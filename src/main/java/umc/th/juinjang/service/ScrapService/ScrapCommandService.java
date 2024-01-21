package umc.th.juinjang.service.ScrapService;

import umc.th.juinjang.model.entity.enums.ScrapActionType;

public interface ScrapCommandService {

  ScrapActionType actionScrap(Long limjangId);
}
