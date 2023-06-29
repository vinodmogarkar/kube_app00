package com.home.eshopee.shipping.dispatching.jdbc;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.shipping.dispatching.DeliveryDispatched;
import com.home.eshopee.shipping.dispatching.Dispatching;
import com.home.eshopee.shipping.dispatching.OrderId;
import com.home.eshopee.shipping.dispatching.jdbc.DispatchingJdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = DispatchingTest.TestConfig.class)
class DispatchingTest {

    @Autowired
    private Dispatching dispatching;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void dispatching_a_delivery_raises_an_event() {
        dispatching.dispatch(new OrderId("DispatchingTest"));

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(DeliveryDispatched.class);
                    DeliveryDispatched deliveryDispatched = (DeliveryDispatched) event;
                    assertAll(
                            () -> assertThat(deliveryDispatched.when).isNotNull(),
                            () -> assertThat(deliveryDispatched.orderId).isEqualTo("DispatchingTest")
                    );
                    return true;
                }));
    }

    @Configuration
    static class TestConfig {

        @Bean
        DispatchingJdbc dispatchingJdbc(EventPublisher eventPublisher) {
            return new DispatchingJdbc(eventPublisher);
        }
    }
}