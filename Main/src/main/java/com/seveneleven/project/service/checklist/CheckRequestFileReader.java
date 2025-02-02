package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.util.file.dto.FileMetadataDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CheckRequestFileReader {
    List<FileMetadataDto> readCheckRequestFiles(CheckRequest checkRequest);

    List<FileMetadataDto> readCheckRequestRejectFiles(CheckRequest checkRequest);
}
