package com.seveneleven.devlens.domain.admin.common;

public interface EntityConverter <DTO,ENTITY>{
    DTO toDTO(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
