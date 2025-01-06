package com.seveneleven.devlens.domain.member.controller;

import com.seveneleven.devlens.domain.member.dto.MemberJoinDto;
import com.seveneleven.devlens.domain.member.service.MemberService;
import com.seveneleven.devlens.global.config.Annotation.AdminAuthorize;
import com.seveneleven.devlens.global.config.Annotation.UserAuthorize;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/view")
@AllArgsConstructor
public class memberController {

    private final MemberService memberService;

    @PostMapping("/login-process")
    public String login(MemberJoinDto dto) {

        if (dto.getUserid() !=null && dto.getPw()!=null)
            return "dashboard";
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinDto dto) {
        try {
            memberService.join(dto.getUserid(), dto.getPw());
            return ResponseEntity.ok("join success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/setting/admin")
    @AdminAuthorize
    public String adminSettingPage() {
        return "admin_setting";
    }

    @GetMapping("/setting/user")
    @UserAuthorize
    public String userSettingPage() {
        return "user_setting";
    }
}