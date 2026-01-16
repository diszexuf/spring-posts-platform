package com.github.diszexuf.core.repository;

import com.github.diszexuf.core.entity.Subscription;
import com.github.diszexuf.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s.follower from Subscription s where s.followee.id = :followeeId")
    List<User> findFollowersByFolloweeId(@Param("followeeId") Long followeeId);

    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    void deleteByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    int deleteAllByFolloweeIdOrFollowerId(Long followeeId, Long followerId);

    Long followee(User followee);
}
