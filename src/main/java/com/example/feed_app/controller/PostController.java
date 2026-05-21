package com.example.feed_app.controller;

import com.example.feed_app.config.CustomUserDetails;
import com.example.feed_app.domain.Member;
import com.example.feed_app.dto.PostCreateRequest;
import com.example.feed_app.dto.PostResponse;
import com.example.feed_app.dto.PostUpdateRequest;
import com.example.feed_app.service.MemberService;
import com.example.feed_app.service.PostService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody PostCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = memberService.findMemberById(userDetails.getMemberId());
        PostResponse response = postService.createPost(request, member);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("postId") Long postId) {
        PostResponse response = postService.getPost(postId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = memberService.findMemberById(userDetails.getMemberId());
        PostResponse response = postService.updatePost(postId, request, member);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, String>> deletePost(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Member member = memberService.findMemberById(userDetails.getMemberId());
        postService.deletePost(postId, member);

        return ResponseEntity.ok(
                Map.of("message", "게시글이 삭제되었습니다.")
        );
    }
}
