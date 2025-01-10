package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckRequestHistory;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.dto.PostProjectChecklistApplication;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.GetIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestStoreImpl implements CheckRequestStore {

    private final CheckRequestRepository checkRequestRepository;
    private final MemberRepository memberRepository;
    private final GetIpUtil getIpUtil;

    @Override
    @Transactional
    public CheckRequest checkRequestStore(
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    ) {
        // TODO - 파일, 링크 처리 필요.
        Member member = memberRepository.findByIdAndStatus(
                requestDto.getRequesterId(),
                MemberStatus.ACTIVE
        ).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String ipAddress = getIpUtil.getIpAddress(request);

        CheckRequest checkRequest = requestDto.createCheckRequest(requestDto, member, ipAddress);
        return checkRequestRepository.save(checkRequest);
    }

    @Override
    public CheckRequestHistory checkRequestHistoryStore(CheckRequest checkRequest) {
        return checkRequest.createCheckRequestHistory();
    }
}
