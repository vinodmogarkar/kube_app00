package com.home.eshopee.sales.catalog.jdbc;

import com.home.eshopee.sales.catalog.category.Category;
import com.home.eshopee.sales.catalog.category.CategoryId;
import com.home.eshopee.sales.catalog.category.Title;
import com.home.eshopee.sales.catalog.jdbc.UnknownCategory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UnknownCategoryTest {

    @Test
    void unknown_category_has_values() {
        Category unknownCategory = new UnknownCategory();
        assertAll(
                () -> assertThat(unknownCategory.id()).isEqualTo(new CategoryId(0)),
                () -> assertThat(unknownCategory.uri()).isNotNull(),
                () -> assertThat(unknownCategory.title()).isNotNull()
        );
    }

    @Test
    void change_title_noop() {
        Category unknownCategory = new UnknownCategory();
        Title unknownTitle = unknownCategory.title();
        unknownCategory.changeTitle(new Title("test"));

        assertThat(unknownCategory.title()).isEqualTo(unknownTitle);
    }
}
