package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.util.file.dto.FileMetadataResponse;

import java.util.List;

public interface CheckRequestFileReader {
    List<FileMetadataResponse> readCheckRequestFiles(CheckRequest checkRequest);

    List<FileMetadataResponse> readCheckRequestRejectFiles(CheckRequest checkRequest);
}
