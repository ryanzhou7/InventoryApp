package com.ryanzhou.company.inventoryapp.data;

import com.ryanzhou.company.inventoryapp.R;
import com.ryanzhou.company.inventoryapp.model.Product;

/**
 * Created by ryanzhou on 6/27/16.
 */
public class Products {

    public static final Integer[] imageId = {
            R.drawable.ic_build_black_48dp,
            R.drawable.ic_https_black_48dp,
            R.drawable.ic_work_black_48dp,
    };

    // stores the image database names
    public static String[] imageNameDatabase = { "Tool", "Lock", "Case"};

    public static final Product[] sampleProducts = {
            //    public Product(int numDollars, int numCents, int quantity, String name, String supplierPhoneNumber,int imageResourceId) {
            new Product(1, 50, 2, imageNameDatabase[0], "0001112222", imageId[0]),
            new Product(4, 33, 4, imageNameDatabase[1], "0001112222", imageId[1]),
            new Product(6, 99, 7, imageNameDatabase[2], "0001112222", imageId[2])
    };

}
