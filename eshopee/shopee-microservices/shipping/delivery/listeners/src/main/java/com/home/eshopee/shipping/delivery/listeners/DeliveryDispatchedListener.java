package com.home.eshopee.shipping.delivery.listeners;

import com.home.eshopee.shipping.delivery.DispatchDelivery;
import com.home.eshopee.shipping.delivery.OrderId;
import com.home.eshopee.shipping.dispatching.DeliveryDispatched;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class DeliveryDispatchedListener {

    private final @NonNull DispatchDelivery dispatchDelivery;

    @TransactionalEventListener
    @Async
    public void on(DeliveryDispatched event) {
        dispatchDelivery.byOrder(new OrderId(event.orderId));
    }
}