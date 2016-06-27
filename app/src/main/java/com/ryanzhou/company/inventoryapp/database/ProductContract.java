package com.ryanzhou.company.inventoryapp.database;

import android.provider.BaseColumns;

/**
 * Created by ryanzhou on 6/27/16.
 */
public class ProductContract {

    public ProductContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_NAME_NAME = "nameColumn";
        public static final String COLUMN_NAME_QUANTITY = "quantityColumn";
        public static final String COLUMN_NAME_PRICE_DOLLAR = "priceDollarColumn";
        public static final String COLUMN_NAME_PRICE_CENTS = "priceCentsColumn";
    }
}
