package com.example.shopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.storage.Constants;
import com.example.shopping.storage.Product;
import com.example.shopping.storage.ProductsAdapter;
import com.example.shopping.storage.Database;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity implements ProductClickListener,View.OnClickListener{
    int totalCost=0;
    private List<Product> products;
    private ProductsAdapter productsAdapter;
    private Database database;
    TextView totalcost;
    private Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_database);

        totalcost=findViewById(R.id.total_cost);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);
        database = new Database(ShoppingCart.this);
        products = new ArrayList<>();
        productsAdapter = new ProductsAdapter(products, ShoppingCart.this,this);

        RecyclerView contactsRecyclerView = findViewById(R.id.contacts_recycler_view);
        contactsRecyclerView.setAdapter(productsAdapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(ShoppingCart.this));
        contactsRecyclerView.addItemDecoration(new DividerItemDecoration(ShoppingCart.this, DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void onResume() {
        super.onResume();
        products.clear();
        totalCost=0;
        Cursor cursor = database.getProductsOfUser();
        String convertedCost;
        String actualCost[];
        while (!cursor.isAfterLast()) {
            convertedCost=cursor.getString(2);
            convertedCost=convertedCost.substring(0,convertedCost.length()-1) + " " + convertedCost.substring(convertedCost.length()-1,convertedCost.length());
            actualCost=convertedCost.split(" ");
            totalCost+=Integer.parseInt(actualCost[0])*cursor.getInt(3);
            products.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            cursor.moveToNext();
        }
        totalcost.setText(String.valueOf(totalCost) + " " + "$");
        productsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.update_and_delete, menu);
    }

    @Override
    public void onItemClick(View view, final Product product) {
        PopupMenu popup = new PopupMenu(ShoppingCart.this, view);
        popup.getMenuInflater().inflate(R.menu.update_and_delete, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.update:
                        updateProduct(product);
                        return true;
                    case R.id.delete:
                        deleteProduct(product.getId());
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    private void deleteProduct(int id) {
        database.deleteProduct(id);
        onResume();
    }

    private void updateProduct(Product product) {
        Intent intent = new Intent(ShoppingCart.this, ShowDialog.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PRODUCT, product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.submit)
        {
            goToSubmit();

        }
    }
    private void goToSubmit() {
        Intent intent =new Intent(ShoppingCart.this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

}

