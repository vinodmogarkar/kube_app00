package com.home.eshopee.sales.catalog.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import com.home.eshopee.sales.catalog.FindProductsFromCategory;
import com.home.eshopee.sales.catalog.category.Uri;
import com.home.eshopee.sales.catalog.jdbc.FindProductsFromCategoryJdbc;
import com.home.eshopee.sales.catalog.product.Product;
import com.home.eshopee.sales.catalog.product.ProductId;

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
@ContextConfiguration(classes = FindProductsFromCategoryTest.TestConfig.class)
@Sql("/test-data-sales-find-products.sql")
class FindProductsFromCategoryTest {

    @Autowired
    private FindProductsFromCategory fromCategory;

    @Test
    void products_from_a_category_by_uri_are_found() {
        List<ProductId> productIds = fromCategory.byUri(new Uri("cat1")).stream()
                .map(Product::id)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactlyInAnyOrder(
                new ProductId("p-1"), new ProductId("p-2"));
    }

    @Configuration
    static class TestConfig {
        @Bean
        FindProductsFromCategoryJdbc findProductsFromCategoryJdbc(JdbcTemplate jdbcTemplate) {
            return new FindProductsFromCategoryJdbc(jdbcTemplate);
        }
    }
}
