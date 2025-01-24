package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.Service.LinkService;
import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkPayload;
import com.seveneleven.util.file.dto.LinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLinkService {
    private final LinkService linkService;

    private static final int MAX_LINK_COUNT = 10; //게시물별 최대 링크 수(10개)
    private final PostRepository postRepository;

    /**
     * 1. 게시물 링크 업로드
     * 함수명 : uploadPostLink
     * @auth admin, 게시물 작성자
     * @param linkInputs 업로드할 링크 DTO
     * @param postId 해당 게시물 id
     * @param uploaderId 업로드 수행자 id
     */
    @Transactional
    public void uploadPostLink(List<LinkInput> linkInputs, Long postId, Long uploaderId) {
        //1. 게시물 id로 존재여부 판별
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 판별 - admin 해당 게시글 작성자

        //3. 현재 저장된 파일 갯수 확인(저장할 파일 갯수 + 현재 저장 갯수 > 10인지 판별)
        Integer currentLinkCnt = linkService.countLinks(LinkCategory.POST_ATTACHMENT_LINK, postEntity.getId());
        if(currentLinkCnt + linkInputs.size() >= MAX_LINK_COUNT){
            throw new BusinessException(ErrorCode.LINK_QUANTITY_EXCEED_ERROR);
        }

        //4. LinkInputs <-> LinkPayloads 변환
        List<LinkPayload> linkPayloads = linkInputs.stream()
                .map(linkInput -> LinkPayload.toLinkPayload(
                        LinkCategory.POST_ATTACHMENT_LINK,
                        postEntity.getId(),
                        linkInput.getLinkTitle(),
                        linkInput.getLink()
                )).collect(Collectors.toList());

        //4. 링크 리스트 업로드
        for(LinkPayload linkPayload : linkPayloads){
            linkService.uploadLink(linkPayload);
        }
    }

    /**
     * 2. 게시물 링크 조회
     * 함수명 : getPostLinks
     * @param postId 해당 게시물 id
     * @return List<LinkResponse> 해당 게시물의 링크 리스트
     */
    @Transactional(readOnly = true)
    public List<LinkResponse> getPostLinks(Long postId) {
        //1. 게시물 id로 존재여부 판별
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //2. 게시물의 링크 엔티티 가져오기
        //페이지네이션 없음
        List<Link> linkEntities = linkService.getLinks(LinkCategory.POST_ATTACHMENT_LINK, postEntity.getId());

        //3. 게시물의 링크 엔티티를 dto로 변환하기
        List<LinkResponse> linkResponses = new ArrayList<>();
        for(Link linkEntity : linkEntities){
            LinkResponse linkResponse = LinkResponse.toDto(linkEntity);
            linkResponses.add(linkResponse);
        }

        //4. dto 반환
        return linkResponses;
    }


    /**
     * 3. 게시물 링크 일괄 삭제
     * 함수명 : deleteAllPostLinks
     * @param postId 해당 게시물 id
     */
    @Transactional
    public void deleteAllPostLinks(Long postId) {
        //1. 게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 권한 판별

        //3. 해당 게시물의 링크를 모두 삭제한다.
        for(Link linkEntity : linkService.getLinks(LinkCategory.POST_ATTACHMENT_LINK, postEntity.getId())){
            linkService.deleteLinkById(linkEntity.getId());
        }
    }


    /**
     * 4. 게시물 링크 수정
     * 함수명 : updatePostLinks
     * @param links 업데이트할 링크 목록
     */
    @Transactional
    public void updatePostLinks(Long postId, List<LinkInput> linkInputs){
        //1. 게시물 존재 여부 판별
        Post PostEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 권한 검증

        //3. 게시물 링크 수정
//        linkService.updateLinks()
    }




}
