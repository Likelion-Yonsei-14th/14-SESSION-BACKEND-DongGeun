package com.example.feed_app.dto;

import com.example.feed_app.domain.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private Long writerId;
    private String writerNickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getMember().getId(),
                comment.getMember().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
