package com.github.diszexuf.core.event.listener;

import com.github.diszexuf.core.client.SubscriptionClient;
import com.github.diszexuf.core.event.SubscriptionChangeApplicationEvent;
import com.github.diszexuf.core.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionChangeListener {

    private final SubscriptionClient client;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEvent(SubscriptionChangeApplicationEvent event) {
        log.info("Get event for subscription change: {}", event);

        switch (event.getSubscriptionType()) {
            case SUBSCRIBE -> client.subscribe(event.getFolloweeId(), event.getFollowerId());
            case UNSUBSCRIBE -> client.unsubscribe(event.getFolloweeId(), event.getFollowerId());
            case REMOVE -> client.deleteSubscription(event.getFolloweeId());
            default -> throw new GeneralException("Type not found: " + event.getSubscriptionType());
        }
    }
}
