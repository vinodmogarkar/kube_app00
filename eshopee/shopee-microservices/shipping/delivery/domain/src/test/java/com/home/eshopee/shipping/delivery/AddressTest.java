package com.home.eshopee.shipping.delivery;

import org.junit.jupiter.api.Test;

import com.home.eshopee.shipping.delivery.Address;
import com.home.eshopee.shipping.delivery.Person;
import com.home.eshopee.shipping.delivery.Place;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AddressTest {

    @Test
    void address_values() {
        Person person = new Person("Test Person");
        Place place = new Place("Test Address 123");
        Address address = new Address(person, place);

        assertAll(
                () -> assertThat(address.person()).isEqualTo(person),
                () -> assertThat(address.place()).isEqualTo(place)
        );
    }
}
