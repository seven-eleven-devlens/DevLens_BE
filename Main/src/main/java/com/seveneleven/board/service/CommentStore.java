package com.seveneleven.board.service;

import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.board.CommentHistory;
import com.seveneleven.entity.board.constant.HistoryAction;

public interface CommentStore {
    Comment storeComment(Comment comment);
    CommentHistory storeCommentHistory(Comment comment, HistoryAction action, String registerIp, String modifierIp);
    void deleteComment(Long commentId);
}
