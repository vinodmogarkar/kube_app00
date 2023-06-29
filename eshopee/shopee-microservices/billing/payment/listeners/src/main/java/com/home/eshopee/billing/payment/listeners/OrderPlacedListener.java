package com.home.eshopee.billing.payment.listeners;

import com.home.eshopee.billing.payment.CollectPayment;
import com.home.eshopee.billing.payment.ReferenceId;
import com.home.eshopee.common.primitives.Money;
import com.home.eshopee.sales.order.OrderPlaced;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Payment listener for OrderPlaced event.
 */
@Component("payment-orderPlacedListener") // a custom name to avoid collision
@RequiredArgsConstructor
class OrderPlacedListener {

    private final @NonNull CollectPayment collectPayment;

    @TransactionalEventListener
    @Async
    public void on(OrderPlaced event) {
        collectPayment.collect(
                new ReferenceId(event.orderId),
                new Money(event.total));
    }
}
