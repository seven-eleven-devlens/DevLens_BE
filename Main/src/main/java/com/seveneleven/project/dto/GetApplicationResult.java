package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.entity.project.constant.ApprovalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class GetApplicationResult {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long requestId;
        private String requestTitle;
        private String requestDescription;
        private Boolean result;
        private LocalDateTime processTime;
        private String processorName;
        private String processIp;
        private String rejectReason;

        public Response(CheckRequest checkRequest, CheckResult checkResult) {
            this.requestId = checkRequest.getId();
            this.requestTitle = checkRequest.getTitle();
            this.requestDescription = checkRequest.getContent();
            this.result = checkResult.getApprovalStatus() == ApprovalStatus.APPROVED;
            this.processTime = checkResult.getCreatedAt();
            this.processorName = checkResult.getProcessorMember().getName();
            this.processIp = checkResult.getProcessorIp();
            this.rejectReason = checkResult.getRejectionReason();
        }

        public static Response toDto(CheckRequest checkRequest, CheckResult checkResult) {
            return new Response(checkRequest, checkResult);
        }
    }
}
