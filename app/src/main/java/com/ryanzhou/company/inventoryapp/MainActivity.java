package com.ryanzhou.company.inventoryapp;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ryanzhou.company.inventoryapp.database.ProductDbHelper;
import com.ryanzhou.company.inventoryapp.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyProductRecyclerViewAdapter.OnListFragmentInteractionListener{

    CoordinatorLayout coordinatorLayout;
    FloatingActionButton floatingActionButton;
    ProductDbHelper productDbHelper;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_add_item);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        productDbHelper = new ProductDbHelper(getApplicationContext());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new MyProductRecyclerViewAdapter(new ArrayList<Product>(), MainActivity.this));
    }

    @Override
    public void onClickProduct(Product item) {
        
    }




}
