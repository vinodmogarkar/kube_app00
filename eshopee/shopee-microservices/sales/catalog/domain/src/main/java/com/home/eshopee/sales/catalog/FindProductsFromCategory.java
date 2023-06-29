package com.home.eshopee.sales.catalog;

import com.home.eshopee.sales.catalog.category.Uri;
import com.home.eshopee.sales.catalog.product.Products;

/**
 * Find Products from Category use-case.
 */
public interface FindProductsFromCategory {

    /**
     * Lists products from the category by URI
     *
     * @param categoryUri the URI of the category
     * @return the products from the category
     */
    Products byUri(Uri categoryUri);
}
