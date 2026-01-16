package com.github.diszexuf.core.service;

import com.github.diszexuf.core.entity.Subscription;
import com.github.diszexuf.core.entity.SubscriptionType;
import com.github.diszexuf.core.entity.User;
import com.github.diszexuf.core.event.SubscriptionChangeApplicationEvent;
import com.github.diszexuf.core.exception.GeneralException;
import com.github.diszexuf.core.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final UserService userService;

    private final SubscriptionRepository subscriptionRepository;

    public final ApplicationEventPublisher eventPublisher;

    @Transactional
    @CacheEvict(value = "userSubscriptions", key = "#followeeId")
    public void subscribe(Long followerId, Long followeeId) {
        if (followerId == null || followeeId == null) {
            throw new GeneralException("Invalid user ids provided");
        }

        User follower = userService.findById(followerId);
        User followee = userService.findById(followeeId);

        if (!subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            Subscription subscription = new Subscription();
            subscription.setFollower(follower);
            subscription.setFollowee(followee);
            subscriptionRepository.save(subscription);
            eventPublisher.publishEvent(new SubscriptionChangeApplicationEvent(this,
                    followeeId,
                    followerId,
                    SubscriptionType.SUBSCRIBE));
        }
    }

    @Transactional
    @CacheEvict(value = "userSubscriptions", key = "#followeeId")
    public void unsubscribe(Long followerId, Long followeeId) {
        if (followerId == null || followeeId == null) {
            throw new GeneralException("Invalid user ids provides");
        }

        if (subscriptionRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            log.info("Delete by dollower {} and followee {}", followerId, followeeId);
            subscriptionRepository.deleteByFollowerIdAndFolloweeId(followerId, followeeId);

            eventPublisher.publishEvent(new SubscriptionChangeApplicationEvent(
                    this,
                    followeeId,
                    followerId,
                    SubscriptionType.UNSUBSCRIBE
            ));
        }
    }

    @Cacheable(value = "userSubscriptions", key = "#followeeId")
    public List<User> getFollowers(Long followeeId) {
        log.info("Get followers by followee {}", followeeId);
        return subscriptionRepository.findFollowersByFolloweeId(followeeId);
    }
}
