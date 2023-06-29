package com.home.eshopee.sales.order.jdbc;

import java.util.List;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.common.primitives.Money;
import com.home.eshopee.common.primitives.Quantity;
import com.home.eshopee.sales.order.OrderId;
import com.home.eshopee.sales.order.OrderPlaced;
import com.home.eshopee.sales.order.PlaceOrder;
import com.home.eshopee.sales.order.item.OrderItem;
import com.home.eshopee.sales.order.item.ProductId;
import com.home.eshopee.sales.order.jdbc.PlaceOrderJdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = PlaceOrderTest.TestConfig.class)
class PlaceOrderTest {

    @Autowired
    private PlaceOrder placeOrder;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void order_placed_raises_an_event() {
        placeOrder.place(new OrderId("TEST123"), List.of(
                new OrderItem(new ProductId("PTEST"), new Quantity(123))),
                new Money(123.5f * 123));

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(OrderPlaced.class);
                    OrderPlaced orderPlaced = (OrderPlaced) event;
                    assertAll(
                            () -> assertThat(orderPlaced.when).isNotNull(),
                            () -> assertThat(orderPlaced.orderId).isEqualTo("TEST123"),
                            () -> assertThat(orderPlaced.items).hasSize(1),
                            () -> assertThat(orderPlaced.items.get("PTEST")).isEqualTo(123),
                            () -> assertThat(orderPlaced.total).isCloseTo(123.5f * 123, offset(0.01f))
                    );
                    return true;
                }));
    }

    @Configuration
    static class TestConfig {
        @Bean
        PlaceOrderJdbc placeOrderJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
            return new PlaceOrderJdbc(jdbcTemplate, eventPublisher);
        }
    }
}
