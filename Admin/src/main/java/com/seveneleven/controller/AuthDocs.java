//package com.seveneleven.controller;
//
//
//import com.seveneleven.dto.MemberDto;
//import com.seveneleven.dto.MemberUpdate;
//import com.seveneleven.entity.member.constant.MemberStatus;
//import com.seveneleven.entity.member.constant.Role;
//import com.seveneleven.response.APIResponse;
//import com.seveneleven.response.SuccessCode;
//import com.seveneleven.util.security.dto.TokenResponse;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequestMapping("/api/auth")
//@Tag(name = "Administrator Member Management API", description = "관리자 회원 관리 관련 API")
//public interface AuthDocs {
//    @Operation(
//            summary = "회원 목록 조회",
//            description = "필터 조건에 따라 회원 목록을 조회합니다.",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "회원 목록 조회 성공",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = APIResponse.class)
//                            )
//                    )
//            },
//            parameters = {
//                    @Parameter(name = "name", description = "회원 이름"),
//                    @Parameter(name = "status", description = "회원 상태"),
//                    @Parameter(name = "role", description = "회원 역할"),
//                    @Parameter(name = "loginId", description = "로그인 ID"),
//                    @Parameter(name = "pageable", description = "페이징 정보")
//            }
//    )
//    @PostMapping("/refresh")
//    ResponseEntity<APIResponse<TokenResponse>> refreshAccessToken(@RequestHeader("X-Refresh-Token") String refreshToken);
//
//}
