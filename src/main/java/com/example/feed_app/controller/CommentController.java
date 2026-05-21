package com.example.feed_app.controller;

import com.example.feed_app.config.CustomUserDetails;
import com.example.feed_app.domain.Member;
import com.example.feed_app.dto.CommentCreateRequest;
import com.example.feed_app.dto.CommentResponse;
import com.example.feed_app.service.CommentService;
import com.example.feed_app.service.MemberService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = memberService.findMemberById(userDetails.getMemberId());
        CommentResponse response = commentService.createComment(postId, request, member);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable("postId") Long postId
    ) {
        List<CommentResponse> response = commentService.getComments(postId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = memberService.findMemberById(userDetails.getMemberId());
        commentService.deleteComment(commentId, member);

        return ResponseEntity.ok(
                Map.of("message", "댓글이 삭제되었습니다.")
        );
    }
}