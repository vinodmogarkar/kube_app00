package com.home.eshopee.sales.catalog.jdbc;

import com.home.eshopee.common.primitives.Money;
import com.home.eshopee.sales.catalog.FindProducts;
import com.home.eshopee.sales.catalog.jdbc.FindProductsJdbc;
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
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = ChangeProductTest.TestConfig.class)
@Sql(statements = "INSERT INTO products VALUES ('TEST1', 'Test', 'Test', 1.00);")
class ChangeProductTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void product_title_is_changed() {
        Product product = findProducts.byId(new ProductId("TEST1"));
        product.changeTitle(new Title("Updated title"));

        Product updated = findProducts.byId(new ProductId("TEST1"));

        assertThat(updated.title()).isEqualTo(new Title("Updated title"));
    }

    @Test
    void product_description_is_changed() {
        Product product = findProducts.byId(new ProductId("TEST1"));
        product.changeDescription(new Description("Updated description"));

        Product updated = findProducts.byId(new ProductId("TEST1"));

        assertThat(updated.description()).isEqualTo(new Description("Updated description"));
    }

    @Test
    void product_price_is_changed() {
        Product product = findProducts.byId(new ProductId("TEST1"));
        product.changePrice(new Money(100.5f));

        Product updated = findProducts.byId(new ProductId("TEST1"));

        assertThat(updated.price()).isEqualTo(new Money(100.5f));
    }

    @Configuration
    static class TestConfig {
        @Bean
        FindProductsJdbc findProductsJdbc(JdbcTemplate jdbcTemplate) {
            return new FindProductsJdbc(jdbcTemplate);
        }
    }
}
