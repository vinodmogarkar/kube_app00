package com.home.eshopee.billing.payment.jdbc;

import com.home.eshopee.billing.payment.CollectPayment;
import com.home.eshopee.billing.payment.Payment;
import com.home.eshopee.billing.payment.ReferenceId;
import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.common.primitives.Money;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC implementation for Collect Payment use-cases.
 */
@RequiredArgsConstructor
class CollectPaymentJdbc implements CollectPayment {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void collect(ReferenceId referenceId, Money total) {
        Payment payment = new PaymentJdbc(referenceId, total, jdbcTemplate, eventPublisher);
        payment.request();

        // here an external service like PayPal or Visa is called...

        payment.collect();
    }
}
