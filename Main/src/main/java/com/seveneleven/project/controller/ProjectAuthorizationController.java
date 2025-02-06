package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectAuthorizationController {

    private final ProjectAuthorizationFacade projectAuthorizationFacade;

    /**
     * 함수명 : postProjectAuthorization
     * 프로젝트 접근 권한을 편집하는 함수
     */
    @PostMapping("/steps/{stepId}/authorizations")
    public ResponseEntity<APIResponse<PostProjectAuthorization.Response>> postProjectAuthorization(
            @PathVariable Long stepId,
            @RequestBody PostProjectAuthorization.Request requestDto
    ) {
        // TODO - ProjectId로 변경
        PostProjectAuthorization.Response responseDto = projectAuthorizationFacade.postProjectAuthorization(requestDto, stepId);

        if(responseDto.getFailList().isEmpty()) {
            return ResponseEntity.status(SuccessCode.CREATED.getStatusCode())
                    .body(APIResponse.success(SuccessCode.OK, responseDto));
        }
        return ResponseEntity.status(SuccessCode.MULTISTATUS.getStatusCode())
                .body(APIResponse.success(SuccessCode.MULTISTATUS, responseDto));
    }

    /**
     * 함수명 : getProjectAuthorization
     * 해당 프로젝트의 접근 권한자 목록을 반환하는 함수
     */
    @GetMapping("/steps/{stepId}/authorizations")
    public ResponseEntity<APIResponse<GetProjectAuthorization.Response>> getProjectAuthorization(
            @PathVariable Long stepId
    ) {
        // TODO - 프로젝트 id로 변경
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectAuthorizationFacade.getProjectAuthorization(stepId)));
    }

    /**
     * 함수명 : getMemberAuthorization
     * 해당 멤버가 접근 권한을 확인하는 함수
     */

    /**
     *
     */
}
