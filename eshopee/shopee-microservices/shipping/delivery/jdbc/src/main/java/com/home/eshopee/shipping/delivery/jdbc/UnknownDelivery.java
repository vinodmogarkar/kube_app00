package com.home.eshopee.shipping.delivery.jdbc;

import com.home.eshopee.shipping.delivery.Address;
import com.home.eshopee.shipping.delivery.Delivery;
import com.home.eshopee.shipping.delivery.DeliveryId;
import com.home.eshopee.shipping.delivery.OrderId;
import com.home.eshopee.shipping.delivery.Person;
import com.home.eshopee.shipping.delivery.Place;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Null object implementation for Delivery entity.
 */
@RequiredArgsConstructor
@Slf4j
final class UnknownDelivery implements Delivery {

    @Override
    public DeliveryId id() {
        return new DeliveryId(0);
    }

    @Override
    public OrderId orderId() {
        return new OrderId(0);
    }

    @Override
    public Address address() {
        return new Address(
                new Person("Unknown Person"),
                new Place("Unknown"));
    }

    @Override
    public void prepare() {
        // do nothing
    }

    @Override
    public void dispatch() {
        // do nothing
    }

    @Override
    public boolean isDispatched() {
        return false;
    }
}
