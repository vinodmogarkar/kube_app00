package com.home.eshopee.shipping.delivery.listeners;

import java.time.Instant;

import com.home.eshopee.shipping.delivery.DispatchDelivery;
import com.home.eshopee.shipping.delivery.OrderId;
import com.home.eshopee.shipping.delivery.listeners.DeliveryDispatchedListener;
import com.home.eshopee.shipping.dispatching.DeliveryDispatched;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeliveryDispatchedListenerTest {

    @Test
    void on_delivery_dispatched_dispatches() {
        DispatchDelivery dispatchDelivery = mock(DispatchDelivery.class);
        DeliveryDispatchedListener listener = new DeliveryDispatchedListener(dispatchDelivery);

        listener.on(new DeliveryDispatched(Instant.now(), "TEST123"));

        verify(dispatchDelivery).byOrder(new OrderId("TEST123"));
    }
}
