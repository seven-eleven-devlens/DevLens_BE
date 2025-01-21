package com.seveneleven.util.file.Service;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.dto.LinkPayload;
import com.seveneleven.util.file.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkRepository linkRepository;

    /**
     * 1. 링크 저장
     * 함수명 : saveLink
     * @param linkPayload 링크 정보 dto
     * @return savedLinkEntity
     */
    @Transactional
    public Link saveLink(LinkPayload linkPayload){
        Link linkEntity = Link.registerLink(linkPayload);

        Link savedLinkEntity = linkRepository.save(linkEntity);

        return savedLinkEntity;
    }

    /**
     * 2. 링크 조회(리스트)
     * 함수명 : getLinks
     * @param linkCategory 링크 카테고리
     * @param referenceId 링크 참조 ID
     * @return List<Link> 링크 정보 목록을 담은 응답 객체
     */
    @Transactional(readOnly = true)
    public List<Link> getLinks(LinkCategory linkCategory, Long referenceId){
        //해당 링크 카테고리와 참조 id로 link entity를 가져온다.
        List<Link> linkEntities = linkRepository.findAllByCategoryAndReferenceId(linkCategory, referenceId);

        return linkEntities;
    }


    /**
     * 3. 링크 삭제(카테고리, 참조ID)
     * 함수명 : deleteLinks
     * @param linkCategory 링크 카테고리
     * @param referenceId 링크 참조 ID
     */
    @Transactional
    public void deleteLinks(LinkCategory linkCategory, Long referenceId){
        //카테고리와 참조 ID로 링크 유무 판별
        Link toDeleteLink = linkRepository.findByCategoryAndReferenceId(linkCategory, referenceId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.LINK_NOT_FOUND_ERROR));

        linkRepository.delete(toDeleteLink);
    }

}
