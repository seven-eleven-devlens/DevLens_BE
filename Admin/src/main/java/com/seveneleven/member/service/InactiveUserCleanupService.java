package com.seveneleven.member.service;

import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.member.repository.AdminMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class InactiveUserCleanupService {
    private final AdminMemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    @Transactional
    public void deleteInactiveMembers() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1); // 1주일 이전의 날짜를 계산

        int deletedCount = memberRepository.findByStatusAndUpdatedAtBefore(MemberStatus.INACTIVE, oneWeekAgo).size();

        memberRepository.deleteByStatusAndUpdatedAtBefore(MemberStatus.INACTIVE, oneWeekAgo);

        log.info(LocalDateTime.now() + " 기준 1주일이 지난" + deletedCount + "명의 비활성화된 회원을 삭제했습니다.");
    }
}
