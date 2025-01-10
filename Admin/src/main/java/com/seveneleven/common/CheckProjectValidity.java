package com.seveneleven.common;

import com.seveneleven.exception.ProjectNameDuplicatedException;
import com.seveneleven.repository.AdminProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckProjectValidity {
    private final AdminProjectRepository projectRepository;

    /*
        함수명 : checkProjectDuplicatedName
        함수 목적 : 회사 명 일치 여부 파악
     */
    public void checkProjectDuplicatedName(String projectName) {
        projectRepository.findByProjectName(projectName)
                .ifPresent(project -> {
                    throw new ProjectNameDuplicatedException();
                });
    }
}
