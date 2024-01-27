package umc.th.juinjang.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.th.juinjang.apiPayload.ApiResponse;

import umc.th.juinjang.apiPayload.code.status.SuccessStatus;
import umc.th.juinjang.converter.limjang.LimjangMainListConverter;
import umc.th.juinjang.converter.limjang.LimjangPostConverter;
import umc.th.juinjang.model.dto.limjang.LimjangDeleteRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.service.LimjangService.LimjangCommandService;
import umc.th.juinjang.service.LimjangService.LimjangQueryService;

@RestController
@RequestMapping("/api/limjang")
@RequiredArgsConstructor
@Validated
public class LimjangController {

  private final LimjangCommandService limjangCommandService;
  private final LimjangQueryService limjangQueryService;

  @CrossOrigin
  @Operation(summary = "임장 생성 API")
  @PostMapping("")
  public ApiResponse<LimjangPostResponseDTO.PostDTO> postLimjang(
      @RequestBody @Valid LimjangPostRequestDTO.PostDto postDto){
    return ApiResponse.onSuccess(LimjangPostConverter.toPostDTO(limjangCommandService.postLimjang(postDto)));
  }

  @CrossOrigin
  @Operation(summary = "임장 전체 조회 API")
  @GetMapping("")
  public ApiResponse<LimjangTotalListResponseDTO.TotalListDto> getLimjangTotalList(
      ){

    return ApiResponse.onSuccess(limjangQueryService.getLimjangTotalList());
  }

  @CrossOrigin
  @Operation(summary = "임장 메인화면에서 최근 임장 조회 API", description = "가장 최근에 수정된 순으로 최대 5개까지 볼 수 있다.")
  @GetMapping("/main")
  public ApiResponse<LimjangMainViewListResponsetDTO.RecentUpdatedDto> deleteList(
  ){

    return ApiResponse.onSuccess(LimjangMainListConverter.toLimjangMainList(limjangQueryService.getLimjangMainList()));
  }

//  @CrossOrigin
//  @Operation(summary = "임장 선택 삭제", description = "임장 게시글을 여러 개 선택해서 삭제하는 api입니다.")
//  @PostMapping("/delete")
//  public ApiResponse deleteLimjang(@RequestBody @Valid LimjangDeleteRequestDTO.DeleteDto deleteDto
//      ){
//
//    limjangCommandService.deleteLimjangs(deleteDto);
//    return ApiResponse.of(SuccessStatus.LIMJANG_DELETE, null);
//  }

  @CrossOrigin
  @Operation(summary = "임장 선택 삭제", description = "임장 게시글을 여러 개 선택해서 삭제하는 api입니다.")
  @DeleteMapping("/{deleteIds}")
  public ApiResponse deleteLimjang(@PathVariable(name = "deleteIds") @Valid List<Long> deleteDto
  ){

    limjangCommandService.deleteLimjangs(deleteDto);
    return ApiResponse.of(SuccessStatus.LIMJANG_DELETE, null);
  }
}
