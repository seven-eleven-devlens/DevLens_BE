package com.seveneleven.devlens.domain.member.service;

import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.member.repository.CompanyRepository;
import com.seveneleven.devlens.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final MemberRepository repository;

    @Autowired
    public MemberService(PasswordEncoder passwordEncoder, CompanyRepository companyRepository, MemberRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.repository = repository;
    }

    public Member findOne(String loginId) {
        return repository.findByloginId(loginId);
    }

    public Long join(String userid, String pw) {
        Optional<Company> companyOptional = companyRepository.findById(1L);

        Company company = companyOptional.orElseThrow(() ->
                new IllegalStateException("Company with ID 1L not found")
        );

        Member member = Member.createMember(userid, pw, company, Role.USER, "강철수", "admin@admin.com", LocalDate.now(),"010-111-1111",1L,1L,passwordEncoder);
        repository.save(member);

        return member.getId();
    }
}
