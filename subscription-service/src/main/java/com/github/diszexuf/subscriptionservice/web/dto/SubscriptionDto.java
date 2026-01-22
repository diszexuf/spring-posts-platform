package com.github.diszexuf.subscriptionservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {

    private Long id;

    private Set<Long> subscriberIds = new HashSet<>();
}
