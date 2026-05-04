package com.example.crud.controller;

import com.example.crud.domain.Member;
import com.example.crud.domain.Post;
import com.example.crud.repository.MemberRepository;
import com.example.crud.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")

public class PostController {
        private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostController(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/{memberId}")
    public Post createPost(@PathVariable Long memberId, @RequestBody Post post) {
        // 1. 해당 ID의 회원이 있는지 DB에서 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        // 2. 게시글 객체에 조회된 회원(작성자) 객체를 설정 (연관관계 매핑)
        post.setMember(member);

        // 3. DB에 게시글 저장
        return postRepository.save(post);
    }
}
