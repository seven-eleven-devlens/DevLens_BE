package com.seveneleven.common;

public interface EntityConverter <DTO,ENTITY>{
    DTO toDTO(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
