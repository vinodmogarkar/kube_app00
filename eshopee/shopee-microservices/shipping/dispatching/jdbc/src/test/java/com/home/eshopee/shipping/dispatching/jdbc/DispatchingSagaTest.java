package com.home.eshopee.shipping.dispatching.jdbc;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.shipping.dispatching.Dispatching;
import com.home.eshopee.shipping.dispatching.DispatchingSaga;
import com.home.eshopee.shipping.dispatching.OrderId;
import com.home.eshopee.shipping.dispatching.jdbc.DispatchingSagaJdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@JdbcTest
@ContextConfiguration(classes = DispatchingSagaTest.TestConfig.class)
class DispatchingSagaTest {

    @Autowired
    private DispatchingSaga saga;

    @MockBean
    private Dispatching dispatching;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void delivery_is_dispatched() {
        saga.prepared(new OrderId("TEST"));
        saga.accepted(new OrderId("TEST"));
        saga.fetched(new OrderId("TEST"));
        saga.paid(new OrderId("TEST"));

        verify(dispatching).dispatch(new OrderId("TEST"));
    }

    @Test
    void not_paid_delivery_is_not_dispatched() {
        saga.prepared(new OrderId("TEST"));
        saga.accepted(new OrderId("TEST"));
        saga.fetched(new OrderId("TEST"));
        //saga.paid(new SagaId("TEST"));

        verifyNoInteractions(dispatching);
    }

    @Test
    void not_fetched_delivery_is_not_dispatched() {
        saga.prepared(new OrderId("TEST"));
        saga.accepted(new OrderId("TEST"));
        //saga.fetched(new SagaId("TEST"));
        saga.paid(new OrderId("TEST"));

        verifyNoInteractions(dispatching);
    }

    @Test
    void not_accepted_delivery_is_not_dispatched() {
        saga.prepared(new OrderId("TEST"));
        //saga.accepted(new SagaId("TEST"));
        saga.fetched(new OrderId("TEST"));
        saga.paid(new OrderId("TEST"));

        verifyNoInteractions(dispatching);
    }

    @Test
    void not_prepared_delivery_is_not_dispatched() {
        //saga.prepared(new SagaId("TEST"));
        saga.accepted(new OrderId("TEST"));
        saga.fetched(new OrderId("TEST"));
        saga.paid(new OrderId("TEST"));

        verifyNoInteractions(dispatching);
    }

    @Configuration
    static class TestConfig {
        @Bean
        DispatchingSagaJdbc dispatchingSagaJdbc(Dispatching dispatching, JdbcTemplate jdbcTemplate) {
            return new DispatchingSagaJdbc(dispatching, jdbcTemplate);
        }
    }
}
