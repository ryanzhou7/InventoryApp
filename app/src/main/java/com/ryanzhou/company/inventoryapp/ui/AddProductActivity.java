package com.ryanzhou.company.inventoryapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ryanzhou.company.inventoryapp.R;
import com.ryanzhou.company.inventoryapp.data.Products;
import com.ryanzhou.company.inventoryapp.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddProductActivity extends AppCompatActivity {

    public static final int ADD_PRODUCT_REQUEST = 100;
    public static final int ADD_PRODUCT_OK_RESPONSE = 1001;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.editTextProductName) EditText editTextProductName;
    @BindView(R.id.editTextQuantity) EditText editTextProductQuantity;
    @BindView(R.id.editTextPriceDollars) EditText editTextProductPriceDollars;
    @BindView(R.id.editTextPriceCents) EditText editTextProductPriceCents;
    @BindView(R.id.editTextSupplierNumber) EditText editTextSupplierNumber;

    @OnClick(R.id.buttonAddProduct)
    public void addProduct(View view){
        if( isAllDataEntryCorrect() ){
            String name = editTextProductName.getText().toString();
            String supplierNumber = editTextSupplierNumber.getText().toString();
            int dollarsPrice = Integer.valueOf( editTextProductPriceDollars.getText().toString() );
            int centsPrice = Integer.valueOf( editTextProductPriceCents.getText().toString() );
            int quantity = Integer.valueOf( editTextProductQuantity.getText().toString() );
            Intent intent = new Intent();
            int imageId = Products.imageId[spinner.getSelectedItemPosition()];
            intent.putExtra(Product.PRODUCT_BUNDLE_KEY,
                    new Product(dollarsPrice, centsPrice, quantity, name, supplierNumber, imageId));
            setResult(ADD_PRODUCT_OK_RESPONSE, intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.icons_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    private Boolean isAllDataEntryCorrect(){
        String toastMessage = null;
        if( editTextProductName.getText().toString().length() == 0 || editTextProductName.getText().toString().isEmpty() )
            toastMessage = getString(R.string.product_name_error);
        if( editTextProductPriceCents.getText().toString().length() != 2)
            toastMessage = getString(R.string.product_cents_error);
        if( editTextProductPriceDollars.getText().toString().length() == 0)
            toastMessage = getString(R.string.product_dollar_error);
        if( editTextProductQuantity.getText().toString().length() == 0)
            toastMessage = getString(R.string.product_quantity_error);
        if (editTextSupplierNumber.getText().length() != 10)
            toastMessage = getString(R.string.product_supplier_number_error);
        if( toastMessage == null)
            return true;
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_SHORT).show();
        return false;
    }

}
