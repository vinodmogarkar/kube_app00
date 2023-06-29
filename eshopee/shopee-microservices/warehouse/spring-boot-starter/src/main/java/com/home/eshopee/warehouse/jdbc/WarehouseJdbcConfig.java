package com.home.eshopee.warehouse.jdbc;

import com.home.eshopee.common.events.EventPublisher;
import com.home.eshopee.warehouse.Warehouse;
import com.home.eshopee.warehouse.jdbc.GoodsFetchingJdbc;
import com.home.eshopee.warehouse.jdbc.WarehouseJdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for JDBC implementation for Warehouse service.
 */
@Configuration
class WarehouseJdbcConfig {

    @Bean
    WarehouseJdbc warehouseJdbc(JdbcTemplate jdbcTemplate) {
        return new WarehouseJdbc(jdbcTemplate);
    }

    @Bean
    GoodsFetchingJdbc goodsFetchingJdbc(Warehouse warehouse, JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new GoodsFetchingJdbc(warehouse, jdbcTemplate, eventPublisher);
    }
}
