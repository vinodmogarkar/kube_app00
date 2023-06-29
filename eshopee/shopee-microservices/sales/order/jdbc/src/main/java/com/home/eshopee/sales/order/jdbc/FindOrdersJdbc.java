package com.home.eshopee.sales.order.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.common.primitives.Money;
import com.home.eshopee.common.primitives.Quantity;
import com.home.eshopee.sales.order.FindOrders;
import com.home.eshopee.sales.order.Order;
import com.home.eshopee.sales.order.OrderId;
import com.home.eshopee.sales.order.item.OrderItem;
import com.home.eshopee.sales.order.item.ProductId;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC implementation for Find Orders use-cases.
 */
@RequiredArgsConstructor
@Slf4j
final class FindOrdersJdbc implements FindOrders {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public Order byId(OrderId id) {
        var items = jdbcTemplate.queryForList(
                "SELECT product_id, quantity FROM order_items WHERE order_id = ?",
                id.value());

        var order = jdbcTemplate.queryForList(
                "SELECT id, total FROM orders WHERE id = ?",
                id.value())
                .stream().findAny();

        return order
                .map(o -> toOrder(o, items.stream()
                        .map(this::toOrderItem)
                        .collect(Collectors.toList())))
                .orElseGet(UnknownOrder::new);
    }

    private Order toOrder(Map<String, Object> order, List<OrderItem> items) {
        return new OrderJdbc(
                new OrderId(order.get("id")),
                new Money(((BigDecimal) order.get("total")).floatValue()),
                items,
                jdbcTemplate,
                eventPublisher);
    }

    private OrderItem toOrderItem(Map<String, Object> item) {
        return new OrderItem(
                new ProductId(item.get("product_id")),
                new Quantity((Integer) item.get("quantity")));
    }
}
