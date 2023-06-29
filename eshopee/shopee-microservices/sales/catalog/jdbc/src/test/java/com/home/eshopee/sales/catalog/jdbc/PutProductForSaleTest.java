package com.home.eshopee.sales.catalog.jdbc;

import com.home.eshopee.common.primitives.Money;
import com.home.eshopee.sales.catalog.FindProducts;
import com.home.eshopee.sales.catalog.jdbc.FindProductsJdbc;
import com.home.eshopee.sales.catalog.jdbc.ProductJdbc;
import com.home.eshopee.sales.catalog.product.Description;
import com.home.eshopee.sales.catalog.product.Product;
import com.home.eshopee.sales.catalog.product.ProductId;
import com.home.eshopee.sales.catalog.product.Title;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = PutProductForSaleTest.TestConfig.class)
class PutProductForSaleTest {

    @Autowired
    private FindProducts findProducts;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void product_put_for_sale_is_found() {
        Product product = new ProductJdbc(
                new ProductId("TEST"),
                new Title("test"),
                new Description("test"),
                new Money(1.f),
                jdbcTemplate
        );
        product.putForSale();

        Product found = findProducts.byId(new ProductId("TEST"));

        assertThat(found.id()).isEqualTo(new ProductId("TEST"));
    }

    @Configuration
    static class TestConfig {
        @Bean
        FindProductsJdbc findProductsJdbc(JdbcTemplate jdbcTemplate) {
            return new FindProductsJdbc(jdbcTemplate);
        }
    }
}
