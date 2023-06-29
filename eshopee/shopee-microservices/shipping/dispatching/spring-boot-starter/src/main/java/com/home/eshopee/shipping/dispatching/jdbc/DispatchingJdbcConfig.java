package com.home.eshopee.shipping.dispatching.jdbc;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.shipping.dispatching.Dispatching;
import com.home.eshopee.shipping.dispatching.jdbc.DispatchingJdbc;
import com.home.eshopee.shipping.dispatching.jdbc.DispatchingSagaJdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for Dispatching Saga JDBC.
 */
@Configuration
class DispatchingJdbcConfig {

    @Bean
    DispatchingSagaJdbc dispatchingSagaJdbc(Dispatching dispatching, JdbcTemplate jdbcTemplate) {
        return new DispatchingSagaJdbc(dispatching, jdbcTemplate);
    }

    @Bean
    DispatchingJdbc dispatchingJdbc(EventPublisher eventPublisher) {
        return new DispatchingJdbc(eventPublisher);
    }
}
