package com.home.eshopee.sales.order;

import java.util.Collection;

import com.home.eshopee.common.primitives.Money;
import com.home.eshopee.sales.order.item.OrderItem;

/**
 * Place Order use-case.
 */
public interface PlaceOrder {

    /**
     * Places a new order.
     *
     * @param orderId the order ID
     * @param items   the order items
     * @param total   the total amount
     */
    void place(OrderId orderId, Collection<OrderItem> items, Money total);
}
