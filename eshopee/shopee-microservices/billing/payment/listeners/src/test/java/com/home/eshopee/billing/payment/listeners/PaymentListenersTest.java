package com.home.eshopee.billing.payment.listeners;

import java.time.Instant;
import java.util.Collections;

import com.home.eshopee.billing.payment.CollectPayment;
import com.home.eshopee.billing.payment.ReferenceId;
import com.home.eshopee.billing.payment.listeners.OrderPlacedListener;
import com.home.eshopee.common.primitives.Money;
import com.home.eshopee.sales.order.OrderPlaced;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentListenersTest {

    @Test
    void on_order_placed_collects_a_payment() {
        CollectPayment collectPayment = mock(CollectPayment.class);
        OrderPlacedListener listener = new OrderPlacedListener(collectPayment);

        listener.on(new OrderPlaced(Instant.now(), "TEST123", Collections.emptyMap(), 123.5f));

        verify(collectPayment).collect(new ReferenceId("TEST123"), new Money(123.5f));
    }
}
