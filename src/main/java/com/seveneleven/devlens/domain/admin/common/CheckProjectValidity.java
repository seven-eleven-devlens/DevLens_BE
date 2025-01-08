package com.seveneleven.devlens.domain.admin.common;

import com.seveneleven.devlens.domain.admin.db.ProjectRepository;
import com.seveneleven.devlens.domain.admin.exception.ProjectNameDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckProjectValidity {
    private final ProjectRepository projectRepository;
    /*
     */
    public void checkProjectDuplicatedName(String projectName) {
        projectRepository.findByProjectName(projectName)
                .ifPresent(project -> {
                    throw new ProjectNameDuplicatedException();
                });
    }
}
