package umc.th.juinjang.service.ScrapService;

import umc.th.juinjang.model.entity.enums.ScrapActionType;

public interface ScrapService {

  ScrapActionType actionScrap(Long limjangId);

  void createScrap(long limjangId);

  void deleteScrap(long limjangId);
}
