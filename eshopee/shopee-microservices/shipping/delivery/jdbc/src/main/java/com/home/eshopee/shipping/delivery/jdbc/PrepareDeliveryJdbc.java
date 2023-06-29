package com.home.eshopee.shipping.delivery.jdbc;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.shipping.delivery.Address;
import com.home.eshopee.shipping.delivery.Delivery;
import com.home.eshopee.shipping.delivery.OrderId;
import com.home.eshopee.shipping.delivery.PrepareDelivery;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC implementation for Prepare Delivery use-cases.
 */
@RequiredArgsConstructor
@Slf4j
class PrepareDeliveryJdbc implements PrepareDelivery {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Transactional
    @Override
    public void prepare(@NonNull OrderId orderId, @NonNull Address address) {
        Delivery delivery = new DeliveryJdbc(orderId, address, jdbcTemplate, eventPublisher);
        delivery.prepare();
    }
}
