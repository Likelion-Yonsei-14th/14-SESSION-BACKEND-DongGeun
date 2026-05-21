package com.example.feed_app.repository;

import com.example.feed_app.domain.Comment;
import com.example.feed_app.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);  // 0개의 사용 위치

    void deleteAllByPost(Post post); //  0개의 사용 위치
}
