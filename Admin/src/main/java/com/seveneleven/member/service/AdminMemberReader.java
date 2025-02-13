package com.seveneleven.member.service;

import com.seveneleven.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AdminMemberReader {
    Member getMember(Long id);
    List<Member> getCompanyMember(Long id);

    Member getMemberById(Long memberId);
    Member getActiveMemberByLoginId(String loginId);
    Page<Member> getMemberAll(Specification<Member> spec, Pageable pageable);
    Boolean getExistsByLoginId(String loginId);
    Boolean getExistsByEmail(String email);
}

