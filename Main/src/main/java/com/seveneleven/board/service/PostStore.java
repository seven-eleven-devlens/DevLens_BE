package com.seveneleven.board.service;

import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.PostHistory;
import com.seveneleven.entity.board.constant.HistoryAction;

public interface PostStore {
    Post storePost(Post post);

    PostHistory storePostHistory(Post post, HistoryAction postAction, String ip);

    void deletePost(Post post);
}
