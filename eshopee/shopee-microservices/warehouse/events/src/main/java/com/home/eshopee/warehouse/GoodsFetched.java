package com.home.eshopee.warehouse;

import java.time.Instant;

import com.home.eshopee.common.events.DomainEvent;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Goods Fetched domain event.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "orderId")
@ToString
public final class GoodsFetched implements DomainEvent {

    public Instant when;
    public String orderId;
}
