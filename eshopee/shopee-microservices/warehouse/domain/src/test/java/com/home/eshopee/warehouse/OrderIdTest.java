package com.home.eshopee.warehouse;

import org.junit.jupiter.api.Test;

import com.home.eshopee.warehouse.OrderId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderIdTest {

    @Test
    void string_id_value() {
        OrderId orderId = new OrderId(123);
        assertThat(orderId.value()).isEqualTo("123");
    }

    @Test
    void fails_for_null() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId(null));
    }
}
