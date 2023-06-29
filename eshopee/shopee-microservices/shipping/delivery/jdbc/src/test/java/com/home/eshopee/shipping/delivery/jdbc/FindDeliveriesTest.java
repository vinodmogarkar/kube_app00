package com.home.eshopee.shipping.delivery.jdbc;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.shipping.delivery.Address;
import com.home.eshopee.shipping.delivery.Delivery;
import com.home.eshopee.shipping.delivery.DeliveryId;
import com.home.eshopee.shipping.delivery.DeliveryInfo;
import com.home.eshopee.shipping.delivery.FindDeliveries;
import com.home.eshopee.shipping.delivery.OrderId;
import com.home.eshopee.shipping.delivery.Person;
import com.home.eshopee.shipping.delivery.Place;
import com.home.eshopee.shipping.delivery.jdbc.FindDeliveriesJdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = FindDeliveriesTest.TestConfig.class)
@Sql(statements = {
        "INSERT INTO deliveries VALUES" +
            "('000101', '1001', 'Test PersonA', 'Place 1')," +
            "('000102', '1002', 'Test PersonB', 'Place 2');",
        "INSERT INTO dispatched_deliveries VALUES ('000102');"})
class FindDeliveriesTest {

    @Autowired
    private FindDeliveries findDeliveries;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void all_deliveries_are_found() {
        List<DeliveryInfo> deliveries = findDeliveries.all().stream()
                .sorted(Comparator.comparing(di -> di.id().value()))
                .collect(Collectors.toList());
        assertAll(
                () -> assertThat(deliveries).hasSizeGreaterThanOrEqualTo(2),
                () -> assertThat(deliveries.get(0).id()).isEqualTo(new DeliveryId("000101")),
                () -> assertThat(deliveries.get(0).orderId()).isEqualTo(new OrderId("1001")),
                () -> assertThat(deliveries.get(1).id()).isEqualTo(new DeliveryId("000102")),
                () -> assertThat(deliveries.get(1).orderId()).isEqualTo(new OrderId("1002"))
        );
    }

    @Test
    void delivery_is_found_by_order_id() {
        Delivery delivery = findDeliveries.byOrder(new OrderId("1001"));
        assertAll(
                () -> assertThat(delivery.id()).isEqualTo(new DeliveryId("000101")),
                () -> assertThat(delivery.orderId()).isEqualTo(new OrderId("1001")),
                () -> assertThat(delivery.address()).isEqualTo(new Address(new Person("Test PersonA"), new Place("Place 1"))),
                () -> assertThat(delivery.isDispatched()).isFalse()
        );
    }

    @Test
    void delivery_is_not_found_by_order_id() {
        Delivery delivery = findDeliveries.byOrder(new OrderId("does not exist"));

        assertThat(delivery.id()).isEqualTo(new DeliveryId(0));
    }

    @Test
    void status_is_merged_with_events_ledger() {
        Delivery delivery = findDeliveries.byOrder(new OrderId("1002"));

        assertThat(delivery.isDispatched()).isTrue();
    }

    @Test
    void delivery_for_an_order_is_prepared() {
        boolean isPrepared = findDeliveries.isPrepared(new OrderId("1001"));

        assertThat(isPrepared).isTrue();
    }

    @Test
    void delivery_for_an_order_is_not_prepared() {
        boolean isPrepared = findDeliveries.isPrepared(new OrderId("WRONG"));

        assertThat(isPrepared).isFalse();
    }

    @Configuration
    static class TestConfig {
        @Bean
        FindDeliveriesJdbc findDeliveriesJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
            return new FindDeliveriesJdbc(jdbcTemplate, eventPublisher);
        }
    }
}
