package com.home.eshopee.shipping.delivery;

import java.time.Instant;

import com.home.eshopee.common.events.DomainEvent;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Delivery Prepared domain event.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "orderId")
@ToString
public final class DeliveryPrepared implements DomainEvent {

    public Instant when;
    public String orderId;
}
