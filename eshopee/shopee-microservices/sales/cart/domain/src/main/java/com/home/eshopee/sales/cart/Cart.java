package com.home.eshopee.sales.cart;

import java.util.List;

import com.home.eshopee.sales.cart.item.CartItem;
import com.home.eshopee.sales.cart.item.ProductId;

/**
 * Cart entity.
 */
public interface Cart {

    CartId id();

    List<CartItem> items();

    boolean hasItems();

    void add(CartItem toAdd);

    void remove(ProductId productId);

    void empty();
}
