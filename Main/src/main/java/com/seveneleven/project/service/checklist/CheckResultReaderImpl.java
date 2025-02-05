package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.repository.CheckResultRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckResultReaderImpl implements CheckResultReader {

    private final CheckResultRepository checkResultRepository;

    @Override
    public CheckResult read(Long applicationId) {
        return checkResultRepository.findByCheckRequestId(applicationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECK_RESULT_NOT_FOUND));
    }
}
