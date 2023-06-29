package com.home.eshopee.billing.payment.jdbc;

import com.home.eshopee.billing.payment.FindPayments;
import com.home.eshopee.billing.payment.Payments;
import com.home.eshopee.common.events.EventPublisher;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC implementation for Find Payments use-cases.
 */
@RequiredArgsConstructor
final class FindPaymentsJdbc implements FindPayments {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public Payments all() {
        return new PaymentsJdbc(
                "SELECT id, reference_id referenceId, total, status FROM payments",
                jdbcTemplate, eventPublisher);
    }
}
