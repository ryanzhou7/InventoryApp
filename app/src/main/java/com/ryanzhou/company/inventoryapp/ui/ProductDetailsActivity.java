package com.ryanzhou.company.inventoryapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryanzhou.company.inventoryapp.R;
import com.ryanzhou.company.inventoryapp.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsActivity extends AppCompatActivity {

    @BindView(R.id.textViewProductName)
    TextView textViewProductName;
    @BindView(R.id.textViewProductPrice)
    TextView textViewProductPrice;
    @BindView(R.id.textViewProductQuantity)
    TextView textViewProductQuantity;
    @BindView(R.id.imageViewProductIcon)
    ImageView imageViewIcon;
    @BindView(R.id.buttonDelete)
    Button buttonDelete;
    CoordinatorLayout coordinatorLayout;
    Boolean isAttemptingDelete = false;
    public static final int EDIT_PRODUCT_REQUEST = 200;
    public static final int EDIT_PRODUCT_QUANTITY_RESPONSE = 2001;
    public static final int DELETE_PRODUCT_RESPONSE = 2002;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        product = getIntent().getParcelableExtra(Product.PRODUCT_BUNDLE_KEY);
        textViewProductName.setText(product.getName());
        textViewProductPrice.setText(product.getPriceForDisplay());
        textViewProductQuantity.setText(String.valueOf(product.getQuantity()));
        imageViewIcon.setImageResource(product.getImageResourceId());
    }


    @OnClick(R.id.buttonDecrement)
    public void decQuantity() {
        if (product.getQuantity() == 0) {
            Toast.makeText(getApplicationContext(), "Quantity cannot be 0!", Toast.LENGTH_SHORT).show();
            return;
        }
        product.setQuantity(product.getQuantity() - 1);
        textViewProductQuantity.setText(String.valueOf(product.getQuantity()));
    }

    @OnClick(R.id.buttonIncrement)
    public void incQuantity() {
        product.setQuantity(product.getQuantity() + 1);
        textViewProductQuantity.setText(String.valueOf(product.getQuantity()));
    }

    @OnClick(R.id.buttonSaveQuantityEdit)
    public void saveQuantityEdit() {
        Intent intent = new Intent();
        intent.putExtra(Product.PRODUCT_BUNDLE_KEY, product);
        setResult(EDIT_PRODUCT_QUANTITY_RESPONSE, intent);
        finish();
    }

    @OnClick(R.id.buttonOrder)
    public void orderProductCallSupplier() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(product.getSupplierPhoneNumberForIntentCall()));
        try {
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Activity not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.buttonDelete)
    public void deleteProduct() {
        if( isAttemptingDelete ){
            Intent intent = new Intent();
            intent.putExtra(Product.PRODUCT_NAME_KEY, product.getName());
            setResult(DELETE_PRODUCT_RESPONSE, intent);
            finish();
        }
        else{
            isAttemptingDelete = true;
            buttonDelete.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            buttonDelete.setText("Confirm delete");
        }


    }
}
