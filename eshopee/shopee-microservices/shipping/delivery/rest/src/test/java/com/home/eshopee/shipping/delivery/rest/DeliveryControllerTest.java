package com.home.eshopee.shipping.delivery.rest;

import java.util.stream.Stream;

import com.home.eshopee.shipping.delivery.Address;
import com.home.eshopee.shipping.delivery.Delivery;
import com.home.eshopee.shipping.delivery.DeliveryId;
import com.home.eshopee.shipping.delivery.DeliveryInfo;
import com.home.eshopee.shipping.delivery.DeliveryInfos;
import com.home.eshopee.shipping.delivery.FindDeliveries;
import com.home.eshopee.shipping.delivery.OrderId;
import com.home.eshopee.shipping.delivery.Person;
import com.home.eshopee.shipping.delivery.Place;
import com.home.eshopee.shipping.delivery.PrepareDelivery;
import com.home.eshopee.shipping.delivery.rest.DeliveryController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindDeliveries findDeliveries;
    @MockBean
    private PrepareDelivery prepareDelivery;

    @Test
    void all_deliveries_are_listed() throws Exception {
        DeliveryInfos deliveryInfos = testDeliveryInfos(new DeliveryId("TEST123"), new OrderId("TEST-ORDER1"));
        when(findDeliveries.all()).thenReturn(deliveryInfos);

        mockMvc.perform(get("/delivery"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("TEST123")))
                .andExpect(jsonPath("$[0].orderId", is("TEST-ORDER1")));
    }

    @Test
    void delivery_by_order_is_listed() throws Exception {
        Delivery delivery = testDelivery(new DeliveryId("TEST123"), new OrderId("TEST-ORDER1"), "Test Person", "Test Place 123", "test-1", 25);
        when(findDeliveries.byOrder(eq(new OrderId("TEST-ORDER1")))).thenReturn(delivery);

        mockMvc.perform(get("/delivery/order/TEST-ORDER1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("TEST123")))
                .andExpect(jsonPath("$.address.person", is("Test Person")))
                .andExpect(jsonPath("$.address.place", is("Test Place 123")))
                .andExpect(jsonPath("$.dispatched", is(false)));
    }

    @Test
    void delivery_is_prepared() throws Exception {
        mockMvc.perform(
                post("/delivery")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{" +
                                 "\"orderId\": \"TEST\"," +
                                 "\"name\": \"Test Name\"," +
                                 "\"address\": \"Test Address\"" +
                                 "}"))
                .andExpect(status().isCreated());
    }

    private DeliveryInfos testDeliveryInfos(DeliveryId deliveryId, OrderId orderId) {
        DeliveryInfos deliveryInfos = mock(DeliveryInfos.class);
        when(deliveryInfos.stream()).thenReturn(Stream.of(new DeliveryInfo(deliveryId, orderId)));
        return deliveryInfos;
    }

    private Delivery testDelivery(DeliveryId deliveryId, OrderId orderId,
                                  String person, String place, String productCode, Integer quantity) {
        Delivery delivery = mock(Delivery.class);
        when(delivery.id()).thenReturn(deliveryId);
        when(delivery.orderId()).thenReturn(orderId);
        when(delivery.address()).thenReturn(new Address(new Person(person), new Place(place)));
        return delivery;
    }
}
