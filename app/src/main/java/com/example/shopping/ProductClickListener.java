package com.example.shopping;

import android.view.View;

import com.example.shopping.storage.Product;

public interface ProductClickListener {
     void onItemClick(View view, Product product);
}
