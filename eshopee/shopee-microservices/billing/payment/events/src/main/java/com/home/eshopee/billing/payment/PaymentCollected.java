package com.home.eshopee.billing.payment;

import java.time.Instant;

import com.home.eshopee.common.events.DomainEvent;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Payment Collected domain event.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "referenceId")
@ToString
public final class PaymentCollected implements DomainEvent {

    public Instant when;
    public String referenceId;
}
