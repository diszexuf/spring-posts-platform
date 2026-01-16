package com.github.diszexuf.core.service;

import com.github.diszexuf.core.entity.Post;
import com.github.diszexuf.core.event.CreatePostApplicationEvent;
import com.github.diszexuf.core.exception.GeneralException;
import com.github.diszexuf.core.repository.PostRepository;
import com.github.diszexuf.core.repository.specificaction.PostFilter;
import com.github.diszexuf.core.repository.specificaction.PostSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    @Cacheable(value = "posts",
            key = "new org.springframework.cache.interceptor.SimpleKey(#pageable.pageNumber, #pageable.pageSize)")
    public Page<Post> findAll(Pageable pageable) {
        log.info("Find all by pageable: {}", pageable);
        return postRepository.findAll(pageable);
    }

    public Page<Post> filter(PostFilter filter, Pageable pageable) {
        log.info("Filter post by data: {}", filter);
        return postRepository.findAll(PostSpecification.withFilter(filter), pageable);
    }

    @CacheEvict(value = "posts")
    @Transactional
    public Post create(Post post, Long authorId) {
        log.info("Create new post");

        var author = userService.findById(authorId);
        post.setAuthor(author);
        var newPost = postRepository.save(post);

        eventPublisher.publishEvent(new CreatePostApplicationEvent(
                this,
                newPost.getId(),
                authorId,
                author.getUsername()
        ));

        return newPost;
    }

    @CacheEvict(value = "posts")
    @Transactional
    public void deleteById(Long postId, Long userId) {
        log.info("Delete post by id");

        if (!postRepository.existsById(postId)) {
            log.error("Exception on delete. PostId: {}, UserId: {}", postId, userId);
            throw new GeneralException("Exception trying to delete post with id: " + postId);
        }

        postRepository.deleteById(postId);
    }

}
