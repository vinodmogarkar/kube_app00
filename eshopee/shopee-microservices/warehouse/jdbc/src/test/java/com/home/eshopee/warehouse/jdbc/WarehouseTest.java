package com.home.eshopee.warehouse.jdbc;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.warehouse.Amount;
import com.home.eshopee.warehouse.InStock;
import com.home.eshopee.warehouse.ProductId;
import com.home.eshopee.warehouse.Warehouse;
import com.home.eshopee.warehouse.jdbc.WarehouseJdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = WarehouseTest.TestConfig.class)
@Sql(statements = "INSERT INTO products_in_stock VALUES ('test-1', 123), ('test-2', 321);")
class WarehouseTest {

    @Autowired
    private Warehouse warehouse;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void left_in_stock_returned() {
        InStock inStock = warehouse.leftInStock(new ProductId("test-1"));
        assertThat(inStock).isEqualTo(new InStock(new Amount(123)));
    }

    @Test
    void zero_left_in_stock_returned_for_an_unknown_product() {
        InStock inStock = warehouse.leftInStock(new ProductId("XXX"));
        assertThat(inStock).isEqualTo(new InStock(new Amount(0)));
    }

    @Test
    void product_is_put_into_stock() {
        warehouse.putIntoStock(new ProductId("test-xxx"), new Amount(123));
        assertThat(warehouse.leftInStock(new ProductId("test-xxx"))).isEqualTo(new InStock(new Amount(123)));
    }

    @Configuration
    static class TestConfig {
        @Bean
        WarehouseJdbc warehouseJdbc(JdbcTemplate jdbcTemplate) {
            return new WarehouseJdbc(jdbcTemplate);
        }
    }
}
