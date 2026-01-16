package com.github.diszexuf.core.client;

import com.github.diszexuf.core.entity.SubscriptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionClient {

    @Value("${app.client.subscription-service.change-subscription}")
    private String changeSubscriptionUrl;

    @Value("${app.client.subscription-service.delete-subscription-by-id}")
    private String deleteSubscriptionByIdUrl;

    @Value("${app.client.subscription-service.username}")
    private String username;

    @Value("${app.client.subscription-service.password}")
    private String password;

    private final RestClient restClient;

    public void subscribe(Long id, Long subscribeId) {
        sendChangeSubscriptionRequest(id, subscribeId, SubscriptionType.SUBSCRIBE);
    }

    public void unsubscribe(Long id, Long subscribeId) {
        sendChangeSubscriptionRequest(id, subscribeId, SubscriptionType.UNSUBSCRIBE);
    }

    public void deleteSubscription(Long subscriptionId) {
        log.info("Send delete subscription request by id {}", subscriptionId);
        var response = restClient.delete()
                .uri(deleteSubscriptionByIdUrl)
                .headers(headers -> headers.setBasicAuth(username, password))
                .retrieve()
                .toEntity(Void.class);

        log.info("Delete subscription response status {}", response.getStatusCode());
    }

    private void sendChangeSubscriptionRequest(Long id, Long subscribeId, SubscriptionType type) {
        log.info("Send change subscription request for id {} and subscriber id {} and type {}", id, subscribeId, type);

        var response = restClient.post()
                .uri(changeSubscriptionUrl)
                .headers(headers -> headers.setBasicAuth(username, password))
                .body(new ChangeSubscriptionRequest(id, subscribeId, type))
                .retrieve()
                .toEntity(ChangeSubscriptionResponse.class);

        log.info("Change subscription response status: {}", response.getStatusCode());
        log.info("Change subscription response body: {}", response.getBody());
    }


}
