package com.github.diszexuf.core.event;


import com.github.diszexuf.core.entity.SubscriptionType;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class SubscriptionChangeApplicationEvent extends ApplicationEvent {

    private final Long followeeId;

    private final Long followerId;

    private final SubscriptionType subscriptionType;

    public SubscriptionChangeApplicationEvent(Object source,
                                              Long followeeId,
                                              Long followerId,
                                              SubscriptionType subscriptionType) {
        super(source);
        this.followeeId = followeeId;
        this.followerId = followerId;
        this.subscriptionType = subscriptionType;
    }
}
