package com.home.eshopee.warehouse.listeners;

import java.util.stream.Collectors;

import com.home.eshopee.sales.order.OrderPlaced;
import com.home.eshopee.warehouse.Amount;
import com.home.eshopee.warehouse.FetchGoods;
import com.home.eshopee.warehouse.OrderId;
import com.home.eshopee.warehouse.ProductId;
import com.home.eshopee.warehouse.ToFetch;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Warehouse listener for OrderPlaced event.
 */
@Component("warehouse-orderPlacedListener") // a custom name to avoid collision
@RequiredArgsConstructor
class OrderPlacedListener {

    private final @NonNull FetchGoods fetchGoods;

    @TransactionalEventListener
    @Async
    public void on(OrderPlaced event) {
        fetchGoods.fetchFromOrder(
                new OrderId(event.orderId),
                event.items.entrySet().stream()
                        .map(item -> new ToFetch(new ProductId(item.getKey()), new Amount(item.getValue())))
                        .collect(Collectors.toList()));
    }
}
