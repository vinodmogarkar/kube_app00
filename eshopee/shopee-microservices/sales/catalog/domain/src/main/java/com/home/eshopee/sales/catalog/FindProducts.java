package com.home.eshopee.sales.catalog;

import com.home.eshopee.sales.catalog.product.Product;
import com.home.eshopee.sales.catalog.product.ProductId;
import com.home.eshopee.sales.catalog.product.Products;

/**
 * Find Products use-case.
 */
public interface FindProducts {

    /**
     * Lists all products.
     *
     * @return all products
     */
    Products all();

    /**
     * Finds a product by ID.
     *
     * @param id the product ID
     * @return the found product
     */
    Product byId(ProductId id);
}
