package com.home.eshopee.sales.cart.jdbc;

import com.home.eshopee.sales.cart.Cart;
import com.home.eshopee.sales.cart.CartId;
import com.home.eshopee.sales.cart.RetrieveCart;
import com.home.eshopee.sales.cart.jdbc.RetrieveCartJdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = RetrieveCartTest.TestConfig.class)
class RetrieveCartTest {

    @Autowired
    private RetrieveCart retrieveCart;

    @Test
    void cart_is_retrieved() {
        Cart cart = retrieveCart.byId(new CartId("TEST"));
        assertThat(cart.id()).isEqualTo(new CartId("TEST"));
    }

    @Configuration
    static class TestConfig {
        @Bean
        RetrieveCartJdbc retrieveCartJdbc(JdbcTemplate jdbcTemplate) {
            return new RetrieveCartJdbc(jdbcTemplate);
        }
    }
}
