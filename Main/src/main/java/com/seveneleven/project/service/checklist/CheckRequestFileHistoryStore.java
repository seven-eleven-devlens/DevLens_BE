package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.member.Member;
import org.springframework.transaction.annotation.Transactional;

public interface CheckRequestFileHistoryStore {

    void saveRequestFileUploadHistory(FileMetadata uploadedFileEntity, Member registrant);

    void saveRejectionFileUploadHistory(FileMetadata uploadedFileEntity, Member registrant);
}
