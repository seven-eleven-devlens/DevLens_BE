package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CheckRequestFileStore {
    @Transactional
    void checkRequestFileStore(
            List<MultipartFile> files,
            CheckRequest checkRequest,
            Member member);

    void checkRequestRejectFileStore(
            List<MultipartFile> files,
            CheckRequest checkRequest,
            Member member);
}
