package com.seveneleven.devlens.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seveneleven.devlens.domain.member.constant.MemberStatus;
import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.global.entity.BaseEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 회원 ID

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id")
    private Company company; // 회사 ID (연관관계)

    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role role; // 계정 권한 (ROLE_ADMIN, ROLE_SUPER, ROLE_USER)

    @Column(name = "login_id", nullable = false, length = 50)
    private String loginId; // 아이디

    @Column(name = "password", nullable = false, length = 100)
    private String password; // 비밀번호

    @Column(name = "status_code", nullable = false, length = 50)
    private MemberStatus status = MemberStatus.ACTIVE; // 상태 코드

    @Column(name = "profile_image_exists", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN profileImageExists = YN.N; // 프로필 이미지 유무

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 이름

    @Column(name = "email", nullable = false, length = 100)
    private String email; // 이메일

    @Column(name = "date_of_birth")
    private LocalDate birthDate; // 생년월일

    @Column(name = "phone_number", length = 20)
    private String phoneNumber; // 전화번호

    @Column(name = "department_id")
    private Long departmentId; // 부서명

    @Column(name = "position_id")
    private Long positionId; // 직책


    // 생성 메서드
    public static Member createMember(String loginId, String password, Company company, Role role, String name, String email,
                                      LocalDate birthDate, String phoneNumber, Long departmentId, Long positionId) {
        Member member = new Member();
        member.name         = name;
        member.role         = role;
        member.email        = email;
        member.loginId      = loginId;
        member.password     = password;
        member.company      = company;
        member.birthDate    = birthDate;
        member.phoneNumber  = phoneNumber;
        member.positionId   = positionId;
        member.departmentId = departmentId;
        return member;
    }

    // 업데이트 메서드
    public void updateMember(String name, String phoneNumber, Role role, Company company,
                             Long departmentId, Long positionId, YN profileImageExists) {
        this.name               = StringUtils.isNotBlank(name) ? name : this.name;
        this.role               = Objects.nonNull(role) ? role : this.role;
        this.company            = Objects.nonNull(company) ? company : this.company;
        this.phoneNumber        = StringUtils.isNotBlank(phoneNumber) ? phoneNumber : this.phoneNumber;
        this.departmentId       = Objects.nonNull(departmentId) ? departmentId : this.departmentId;
        this.positionId         = Objects.nonNull(positionId) ? positionId : this.positionId;
        this.profileImageExists = Objects.nonNull(profileImageExists) ? profileImageExists : this.profileImageExists;
    }


    // 비밀번호 재설정
    public void updatePassword(String password){
        this.password     = password;
    }

    // 삭제 메서드
    public void deleteMember() {
        this.status = MemberStatus.INACTIVE;
    }

    // 복구 메서드
    public void restoreMember() {
        this.status = MemberStatus.ACTIVE;
    }

}