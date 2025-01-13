package com.seveneleven.dto;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import org.springframework.data.jpa.domain.Specification;

/**
 * MemberSpecification 클래스는 JPA Criteria API를 사용하여
 * Member 엔티티의 동적 쿼리를 생성하기 위한 Specification 메서드를 제공합니다.
 */
public class MemberSpecification {

    /**
     * 이름(name)을 기준으로 Member를 필터링하는 Specification을 생성합니다.
     *
     * @param name 필터링할 이름. null일 경우 조건이 적용되지 않습니다.
     * @return 이름이 주어진 값과 유사한 Member를 찾는 Specification.
     */
    public static Specification<Member> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    /**
     * 상태(status)를 기준으로 Member를 필터링하는 Specification을 생성합니다.
     *
     * @param status 필터링할 상태. null일 경우 조건이 적용되지 않습니다.
     * @return 상태가 주어진 값과 동일한 Member를 찾는 Specification.
     */
    public static Specification<Member> hasStatus(MemberStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    /**
     * 역할(role)을 기준으로 Member를 필터링하는 Specification을 생성합니다.
     *
     * @param role 필터링할 역할. null일 경우 조건이 적용되지 않습니다.
     * @return 역할이 주어진 값과 동일한 Member를 찾는 Specification.
     */
    public static Specification<Member> hasRole(Role role) {
        return (root, query, criteriaBuilder) ->
                role == null ? null : criteriaBuilder.equal(root.get("role"), role);
    }

    /**
     * 로그인 ID(loginId)를 기준으로 Member를 필터링하는 Specification을 생성합니다.
     *
     * @param loginId 필터링할 로그인 ID. null일 경우 조건이 적용되지 않습니다.
     * @return 로그인 ID가 주어진 값과 유사한 Member를 찾는 Specification.
     */
    public static Specification<Member> hasLoginId(String loginId) {
        return (root, query, criteriaBuilder) ->
                loginId == null ? null : criteriaBuilder.like(root.get("loginId"), "%" + loginId + "%");
    }
}

