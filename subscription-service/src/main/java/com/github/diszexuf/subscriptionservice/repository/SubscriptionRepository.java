package com.github.diszexuf.subscriptionservice.repository;

import com.github.diszexuf.subscriptionservice.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, Long> {
    List<Subscription> findAllBySubscribersIdIn(Collection<Long> subscriberIds);
}
