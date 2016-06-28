package com.ryanzhou.company.inventoryapp.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ryanzhou.company.inventoryapp.MyProductRecyclerViewAdapter;
import com.ryanzhou.company.inventoryapp.R;
import com.ryanzhou.company.inventoryapp.data.Products;
import com.ryanzhou.company.inventoryapp.database.ProductContract;
import com.ryanzhou.company.inventoryapp.database.ProductDbHelper;
import com.ryanzhou.company.inventoryapp.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyProductRecyclerViewAdapter.OnListFragmentInteractionListener {

    public final String LOG_TAG = getClass().getSimpleName();
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton floatingActionButton;
    MyProductRecyclerViewAdapter myProductRecyclerViewAdapter;
    ProductDbHelper productDbHelper;
    RecyclerView recyclerView;
    @BindView(R.id.textViewNoItemsInfo)
    TextView textViewNoItemsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_add_item);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        productDbHelper = new ProductDbHelper(getApplicationContext());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivityForResult(intent, AddProductActivity.ADD_PRODUCT_REQUEST);
            }
        });

        List<Product> products = getAllProductsListFromDb();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myProductRecyclerViewAdapter = new MyProductRecyclerViewAdapter(products, MainActivity.this);
        recyclerView.setAdapter(myProductRecyclerViewAdapter);
        checkIfDisplayInfoText( products);
    }

    private void checkIfDisplayInfoText( List<Product> products ){
        if (products.size() == 0) {
            textViewNoItemsInfo.setVisibility(View.VISIBLE);
        } else {
            textViewNoItemsInfo.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == AddProductActivity.ADD_PRODUCT_REQUEST) {
            if (resultCode == AddProductActivity.ADD_PRODUCT_OK_RESPONSE) {
                Product product = data.getParcelableExtra(Product.PRODUCT_BUNDLE_KEY);
                insertProduct(product);
            }
        }
        if (requestCode == ProductDetailsActivity.EDIT_PRODUCT_REQUEST) {
            if (resultCode == ProductDetailsActivity.DELETE_PRODUCT_RESPONSE) {
                String productNameToDelete = data.getStringExtra(Product.PRODUCT_NAME_KEY);
                deleteProductWithName(productNameToDelete);
            }
            if (resultCode == ProductDetailsActivity.EDIT_PRODUCT_QUANTITY_RESPONSE) {
                Product product = data.getParcelableExtra(Product.PRODUCT_BUNDLE_KEY);
                updateQuantityOfProduct(product);
            }
        }

        myProductRecyclerViewAdapter.getmValues().clear();
        myProductRecyclerViewAdapter.getmValues().addAll(getAllProductsListFromDb());
        myProductRecyclerViewAdapter.notifyDataSetChanged();
        checkIfDisplayInfoText( myProductRecyclerViewAdapter.getmValues() );
    }

    private void testCleanDb() {
        productDbHelper.onUpgrade(productDbHelper.getWritableDatabase(), 0, 0);
        productDbHelper = new ProductDbHelper(getApplicationContext());
    }

    @Override
    public void onClickProduct(Product item) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
        intent.putExtra(Product.PRODUCT_BUNDLE_KEY, item);
        startActivityForResult(intent, ProductDetailsActivity.EDIT_PRODUCT_REQUEST);
    }

    private void putSampleProductsInDb() {
        Product[] products = Products.sampleProducts;
        SQLiteDatabase sqLiteDatabaseReadable = productDbHelper.getReadableDatabase();
        for (Product product : products) {
            insertProduct(product);
        }
        printTable();
    }

    private long insertProduct(Product product) {
        SQLiteDatabase sqLiteDatabaseWritable = productDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_NAME, product.getName());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_QUANTITY, product.getQuantity());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRICE_DOLLAR, product.getNumDollars());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRICE_CENTS, product.getNumCents());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_PHONE_NUMBER, product.getSupplierPhoneNumber());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_IMAGE_RES_ID, product.getImageResourceId());
        return sqLiteDatabaseWritable.insert(ProductContract.ProductEntry.TABLE_NAME, null, contentValues);

    }

    private void printTable() {
        Log.d(LOG_TAG, "Printing habit table");
        SQLiteDatabase sqLiteDatabaseReadable = productDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM " + ProductContract.ProductEntry.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                int id = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_QUANTITY));
                Log.d(LOG_TAG, id + "\t" + name + "\t" + quantity);
                cursor.moveToNext();
            }
            Log.d(LOG_TAG, "\n");
        } else {
            Log.d(LOG_TAG, "Nothing to print");
        }
        cursor.close();
    }

    private void updateQuantityOfProduct(Product product) {
        SQLiteDatabase sqLiteDatabaseWritable = productDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_NAME, product.getName());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_QUANTITY, product.getQuantity());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRICE_DOLLAR, product.getNumDollars());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_PRICE_CENTS, product.getNumCents());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_PHONE_NUMBER, product.getSupplierPhoneNumber());
        contentValues.put(ProductContract.ProductEntry.COLUMN_NAME_IMAGE_RES_ID, product.getImageResourceId());
        // Which row to update, based on the Product name
        String selection = ProductContract.ProductEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {product.getName()};
        int count = sqLiteDatabaseWritable.update(
                ProductContract.ProductEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
    }

    private List<Product> getAllProductsListFromDb() {
        SQLiteDatabase sqLiteDatabaseReadable = productDbHelper.getReadableDatabase();
        List<Product> products = new ArrayList<>();
        Cursor cursor = sqLiteDatabaseReadable.rawQuery("SELECT * FROM " + ProductContract.ProductEntry.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String name = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_QUANTITY));
                int dollars = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PRICE_DOLLAR));
                int cents = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PRICE_CENTS));
                String supplierPhoneNumber = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_PHONE_NUMBER));
                int imageResId = cursor.getInt(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_IMAGE_RES_ID));
                products.add(new Product(dollars, cents, quantity, name, supplierPhoneNumber, imageResId));
                cursor.moveToNext();
            }
            Log.d(LOG_TAG, "\n");
        } else {
            Log.d(LOG_TAG, "Nothing to print");
        }
        cursor.close();
        return products;
    }

    private void deleteProductWithName(String name) {
        SQLiteDatabase sqLiteDatabaseWriteable = productDbHelper.getWritableDatabase();
        sqLiteDatabaseWriteable.execSQL("DELETE FROM " +
                ProductContract.ProductEntry.TABLE_NAME +
                " WHERE " + ProductContract.ProductEntry.COLUMN_NAME_NAME + " = " + "\'" + name + "\'"
        );
    }

}
