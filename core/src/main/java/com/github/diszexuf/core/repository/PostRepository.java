package com.github.diszexuf.core.repository;

import com.github.diszexuf.core.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    boolean existsByAuthorId(Long authorId);
}
