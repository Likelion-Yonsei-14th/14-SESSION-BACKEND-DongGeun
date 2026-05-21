package com.example.feed_app.service;

import com.example.feed_app.domain.Member;
import com.example.feed_app.domain.Post;
import com.example.feed_app.dto.PostCreateRequest;
import com.example.feed_app.dto.PostResponse;
import com.example.feed_app.dto.PostUpdateRequest;
import com.example.feed_app.exception.CustomException;
import com.example.feed_app.exception.ErrorCode;
import com.example.feed_app.exception.ForbiddenException;
import com.example.feed_app.exception.PostNotFoundException;
import com.example.feed_app.repository.CommentRepository;
import com.example.feed_app.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public PostResponse createPost(PostCreateRequest request, Member member) {
        validateContent(request.getContent());

        Post post = Post.create(member, request.getContent());
        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }


    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse updatePost(Long postId, PostUpdateRequest request, Member member) {
        validateContent(request.getContent());

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        validateOwner(post, member);

        post.updateContent(request.getContent());
        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    @Transactional
    public void deletePost(Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        validateOwner(post, member);

        commentRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    private void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_POST_CONTENT);
        }
    }

    private void validateOwner(Post post, Member member) {
        if (!post.getMember().getId().equals(member.getId())) {
            throw new ForbiddenException();
        }
    }
}
