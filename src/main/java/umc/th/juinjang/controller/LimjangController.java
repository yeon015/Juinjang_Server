package umc.th.juinjang.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.th.juinjang.apiPayload.ApiResponse;

import umc.th.juinjang.converter.limjang.LimjangPostConverter;
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
  public ApiResponse<LimjangTotalListResponseDTO.TotalListDto> getLimjangList(
      ){

    return ApiResponse.onSuccess(limjangQueryService.getLimjangTotalList());
  }

}
