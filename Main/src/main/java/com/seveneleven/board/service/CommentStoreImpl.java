package com.seveneleven.board.service;

import com.seveneleven.board.repository.CommentHistoryRepository;
import com.seveneleven.board.repository.CommentRepository;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.board.CommentHistory;
import com.seveneleven.entity.board.constant.HistoryAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentStoreImpl implements CommentStore {
    private final CommentRepository commentRepository;
    private final CommentHistoryRepository commentHistoryRepository;

    @Override
    public Comment storeComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public CommentHistory storeCommentHistory(Comment comment, HistoryAction action, String registerIp, String modifierIp) {
        return commentHistoryRepository.save(CommentHistory.createCommentHistory(comment, action, registerIp, modifierIp));
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
