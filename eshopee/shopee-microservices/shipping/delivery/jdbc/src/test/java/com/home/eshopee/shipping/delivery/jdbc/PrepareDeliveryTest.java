package com.home.eshopee.shipping.delivery.jdbc;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.shipping.delivery.Address;
import com.home.eshopee.shipping.delivery.Delivery;
import com.home.eshopee.shipping.delivery.DeliveryPrepared;
import com.home.eshopee.shipping.delivery.FindDeliveries;
import com.home.eshopee.shipping.delivery.OrderId;
import com.home.eshopee.shipping.delivery.Person;
import com.home.eshopee.shipping.delivery.Place;
import com.home.eshopee.shipping.delivery.PrepareDelivery;
import com.home.eshopee.shipping.delivery.jdbc.FindDeliveriesJdbc;
import com.home.eshopee.shipping.delivery.jdbc.PrepareDeliveryJdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = PrepareDeliveryTest.TestConfig.class)
class PrepareDeliveryTest {

    @Autowired
    private FindDeliveries findDeliveries;
    @Autowired
    private PrepareDelivery prepareDelivery;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void delivery_for_order_is_prepared() {
        prepareDelivery.prepare(
                new OrderId("TEST123"),
                new Address(new Person("Test Person"), new Place("Test Address 123")));

        Delivery delivery = findDeliveries.byOrder(new OrderId("TEST123"));

        assertAll(
                () -> assertThat(delivery.orderId()).isEqualTo(new OrderId("TEST123")),
                () -> assertThat(delivery.address()).isEqualTo(new Address(new Person("Test Person"), new Place("Test Address 123")))
        );
    }

    @Test
    void prepared_delivery_raises_an_event() {
        prepareDelivery.prepare(
                new OrderId("TEST123"),
                new Address(new Person("Test Person"), new Place("Test Address 123")));

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(DeliveryPrepared.class);
                    DeliveryPrepared deliveryPrepared = (DeliveryPrepared) event;
                    assertAll(
                            () -> assertThat(deliveryPrepared.when).isNotNull(),
                            () -> assertThat(deliveryPrepared.orderId).isEqualTo("TEST123")
                    );
                    return true;
                }));
    }

    @Configuration
    static class TestConfig {

        @Bean
        FindDeliveriesJdbc findDeliveriesJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
            return new FindDeliveriesJdbc(jdbcTemplate, eventPublisher);
        }

        @Bean
        PrepareDeliveryJdbc prepareDeliveryJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
            return new PrepareDeliveryJdbc(jdbcTemplate, eventPublisher);
        }
    }
}
