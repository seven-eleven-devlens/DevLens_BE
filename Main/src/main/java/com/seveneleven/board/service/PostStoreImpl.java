package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostHistoryRepository;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.PostHistory;
import com.seveneleven.entity.board.constant.HistoryAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostStoreImpl implements PostStore {
    private final PostRepository postRepository;
    private final PostHistoryRepository postHistoryRepository;

    @Override
    public Post storePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public PostHistory storePostHistory(Post post, HistoryAction postAction, String ip) {
        return postHistoryRepository.save(PostHistory.createPostHistory(post, postAction, ip));
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
