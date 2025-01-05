package com.seveneleven.devlens.domain.member.controller;

import com.seveneleven.devlens.domain.member.dto.MemberJoinDto;
import com.seveneleven.devlens.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/view")
@AllArgsConstructor
public class memberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login-process")
    public String login(MemberJoinDto dto) {
//        boolean isValidMember = memberService.isValidMember(dto.getUserid(), dto.getPw());

        if (!dto.getUserid().isBlank() && !dto.getPw().isBlank())
            return "dashboard";
        return "login";
    }
}