package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.storage.Constants;
import com.example.shopping.storage.Product;
import com.example.shopping.storage.Database;

public class ShowDialog extends AppCompatActivity implements View.OnClickListener {
    TextView productName,productPrice;
    Spinner productQuantities;
    Button addToCart;
    Database database;
    Product product;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_order);
        database=new Database(this);
        productName=findViewById(R.id.product_name);
        productPrice=findViewById(R.id.product_cost);
        productQuantities=findViewById(R.id.spinner);
        addToCart=findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            product=(Product)bundle.getSerializable(Constants.PRODUCT);
            if(product!=null) {
                productName.setText(product.getName());
                productPrice.setText(product.getCost());
                productQuantities.setSelection(product.getQuantities()-1);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.add_to_cart){
            product.setName(productName.getText().toString());
            product.setCost(productPrice.getText().toString());
            product.setQuantities(Integer.parseInt(productQuantities.getSelectedItem().toString()));
            database.updateProduct(product);
            goToShoppingCart();
        }
    }
    private void goToShoppingCart(){
        Intent intent=new Intent(ShowDialog.this,ShoppingCart.class);
        startActivity(intent);
    }
}
