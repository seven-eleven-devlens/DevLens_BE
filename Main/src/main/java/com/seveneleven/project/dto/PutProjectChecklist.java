package com.seveneleven.project.dto;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.Checklist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PutProjectChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String description;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private Boolean isChecked;

        public Response(Long id, String title, String description, YesNo isChecked) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.isChecked = isChecked == YesNo.YES;
        }

        public static PutProjectChecklist.Response toDto(Checklist checklist) {
            return new PutProjectChecklist.Response(
                    checklist.getId(),
                    checklist.getTitle(),
                    checklist.getDescription(),
                    checklist.getIsChecked()
            );
        }
    }
}
