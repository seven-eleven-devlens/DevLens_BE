package com.seveneleven.board.service;

import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkResponse;

import java.util.List;

public interface PostLinkService {

    List<LinkResponse> uploadPostLinks(List<LinkInput> linkInputs, Long postId, Long uploaderId, String uploaderRole);

    List<LinkResponse> getPostLinks(Long postId);

    void deletePostLink(Long postId, Long linkId, Long deleterId, String uploaderRole);

    void deleteAllPostLinks(Long postId, Long deleterId, String uploaderRole);

}
