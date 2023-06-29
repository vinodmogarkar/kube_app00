package com.home.eshopee.warehouse.listeners;

import com.home.eshopee.shipping.dispatching.DeliveryDispatched;
import com.home.eshopee.warehouse.OrderId;
import com.home.eshopee.warehouse.RemoveFetchedGoods;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Warehouse listener for DeliveryDispatched event.
 */
@Component("warehouse-deliveryDispatchedListener") // a custom name to avoid collision
@RequiredArgsConstructor
class DeliveryDispatchedListener {

    private final @NonNull RemoveFetchedGoods removeFetchedGoods;

    @TransactionalEventListener
    @Async
    public void on(DeliveryDispatched event) {
        removeFetchedGoods.removeForOrder(new OrderId(event.orderId));
    }
}
