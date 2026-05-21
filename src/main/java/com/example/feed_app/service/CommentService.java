package com.example.feed_app.service;

import com.example.feed_app.domain.Comment;
import com.example.feed_app.domain.Member;
import com.example.feed_app.domain.Post;
import com.example.feed_app.dto.CommentCreateRequest;
import com.example.feed_app.dto.CommentResponse;
import com.example.feed_app.exception.CommentNotFoundException;
import com.example.feed_app.exception.ForbiddenException;
import com.example.feed_app.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentResponse createComment(Long postId, CommentCreateRequest request, Member member) {
        Post post = postService.findPostById(postId);
        Comment comment = Comment.create(post, member, request.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        Post post = postService.findPostById(postId);

        return commentRepository.findAllByPost(post)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    public void deleteComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        validateOwner(comment, member);

        commentRepository.delete(comment);
    }

    private void validateOwner(Comment comment, Member member) {
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new ForbiddenException();
        }
    }
}