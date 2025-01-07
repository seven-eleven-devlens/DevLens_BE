package com.seveneleven.devlens.global.util.file.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum FileCategory {
    COMPANY_LOGO_IMAGE(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp", ".svg"),
            List.of("image/png", "image/jpeg", "image/gif", "image/webp", "image/svg+xml")
    ),
    USER_PROFILE_IMAGE(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp", ".svg"),
            List.of("image/png", "image/jpeg", "image/gif", "image/webp", "image/svg+xml")
    ),
    PROJECT_IMAGE(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp", ".svg"),
            List.of("image/png", "image/jpeg", "image/gif", "image/webp", "image/svg+xml")
    ),
    POST_ATTACHMENT(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp", ".svg", ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt", ".zip", ".7z", ".rar", ".tar"),
            List.of(
                    // image
                    "image/png", "image/jpeg", "image/gif", "image/webp", "image/svg+xml",
                    // document
                    "application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                    "text/plain",
                    // compressed files
                    "application/zip", "application/x-7z-compressed", "application/x-rar-compressed", "application/x-tar"
            )
    ),
    CHECK_REQUEST_ATTACHMENT(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp", ".svg", ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt", ".zip", ".7z", ".rar", ".tar"),
            List.of(
                    "image/png", "image/jpeg", "image/gif", "image/webp", "image/svg+xml",
                    "application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                    "text/plain",
                    "application/zip", "application/x-7z-compressed", "application/x-rar-compressed", "application/x-tar"
            )
    ),
    CHECK_REJECTION_ATTACHMENT(
            Set.of(".png", ".jpg", ".jpeg", ".gif", ".webp", ".svg", ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt", ".zip", ".7z", ".rar", ".tar"),
            List.of(
                    "image/png", "image/jpeg", "image/gif", "image/webp", "image/svg+xml",
                    "application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                    "text/plain",
                    "application/zip", "application/x-7z-compressed", "application/x-rar-compressed", "application/x-tar"
            )
    );

    private final Set<String> allowedExtensions;
    private final List<String> allowedMimeTypes;

    public Set<String> getAllowedExtensions() {
        return allowedExtensions;
    }

    public List<String> getAllowedMimeTypes() {
        return allowedMimeTypes;
    }
}