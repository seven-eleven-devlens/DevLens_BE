package com.seveneleven.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Tag(name = "로그인 페이지 API", description = "로그인 페이지 관련 API")
public interface MemberDocs {
}
