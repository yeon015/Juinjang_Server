package umc.th.juinjang.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.converter.limjang.LimjangPostConverter;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostResponseDTO;
import umc.th.juinjang.service.LimjangService.LimjangCommandService;

@RestController
@RequestMapping("/api/limjang")
@RequiredArgsConstructor
@Validated
public class LimjangController {

  private final LimjangCommandService limjangCommandService;

  @CrossOrigin
  @Operation(summary = "임장 생성 API")
  @PostMapping("")
  public ApiResponse<LimjangPostResponseDTO.PostDTO> postLimjang(
      @RequestBody @Valid LimjangPostRequestDTO.PostDto postDto, Errors errors){
    if (errors.hasErrors()){
      throw new LimjangHandler(ErrorStatus.LIMJANG_POST_REQUEST_NULL);
    }

    return ApiResponse.onSuccess(LimjangPostConverter.toPostDTO(limjangCommandService.postLimjang(postDto)));
  }


}
