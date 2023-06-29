package com.home.eshopee.billing.payment.jdbc;

import com.home.eshopee.billing.payment.jdbc.CollectPaymentJdbc;
import com.home.eshopee.billing.payment.jdbc.FindPaymentsJdbc;
import com.home.eshopee.common.events.EventPublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for JDBC implementation for Payment service.
 */
@Configuration
class PaymentJdbcConfig {

    @Bean
    FindPaymentsJdbc findPaymentsJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new FindPaymentsJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    CollectPaymentJdbc collectPaymentJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new CollectPaymentJdbc(jdbcTemplate, eventPublisher);
    }
}
