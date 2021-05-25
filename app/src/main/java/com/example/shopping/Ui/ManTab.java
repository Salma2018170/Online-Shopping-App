package com.example.shopping.Ui;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shopping.R;
import com.example.shopping.RecyclerViewActivity;
import com.example.shopping.ViewPager.CategoryAdapter;
import com.example.shopping.ViewPager.RecyclerViewEvent;
import com.example.shopping.storage.Product;
import com.example.shopping.storage.Database;

import java.util.ArrayList;
import java.util.List;

public class ManTab extends Fragment implements RecyclerViewEvent, View.OnClickListener {
    Product product;
    TextView productName ;
    TextView productPrice ;
    Button addToCart;
    View view;
    private RecyclerView myRecyclerView;
    private List<Model> itemList;
    private Dialog mDialog;
    EditText searchOnMenFragment;
    private CategoryAdapter categoryAdapter;
    private final int VOICE_REQUEST = 1399;
    Button voiceOnManFragment;
    String COST;
    int QUANTITIES;
    String NAME;
    Spinner productQuantities;
    Database productDatabase;
    public ManTab() {
        // Required empty public constructor
    }

    public static ManTab newInstance() {
        return new ManTab();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_man_tab,container,false);
        myRecyclerView = view.findViewById(R.id.recycler_men_tab);
        categoryAdapter =new CategoryAdapter(getContext(),itemList,this);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerView.setAdapter(categoryAdapter);
        productDatabase=new Database(getContext());

        searchOnMenFragment=view.findViewById(R.id.search1);
        voiceOnManFragment=view.findViewById(R.id.record1);
        voiceOnManFragment.setOnClickListener(this);
        searchOnMenFragment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }


        });

        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemList = new ArrayList<>();
        itemList.add(new Model("ManJacket","25$",R.drawable.man_jacket));
        itemList.add(new Model("ManJeans","20$",R.drawable.men_jeans));
        itemList.add(new Model("Sout","30$",R.drawable.men_sout));
        itemList.add(new Model("SweatShirt","15$",R.drawable.men_sweatshirt));
    }

    @Override
    public void onRecyclerViewClick(final Model model) {
        mDialog = new Dialog(getActivity());
        mDialog.setContentView(R.layout.dialog_order);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        productName = mDialog.findViewById(R.id.product_name);
        productPrice = mDialog.findViewById(R.id.product_cost);
        ImageView productImg = mDialog.findViewById(R.id.product_img);
        productQuantities=mDialog.findViewById(R.id.spinner);

        addToCart=mDialog.findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(this);

        productName.setText(model.getTitle());
        productPrice.setText(model.getPrice());
        productImg.setImageResource(model.getImg());

        mDialog.show();
    }

    public void filter(String text) {
        ArrayList<Model> filteredList = new ArrayList<>();

        for (Model items : itemList) {
            if (items.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(items);
            }
        }

        categoryAdapter.filterList(filteredList);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.record1) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, VOICE_REQUEST);
        }
        else if(view.getId()==R.id.add_to_cart){
                product=new Product();
                product.setQuantities(Integer.parseInt(productQuantities.getSelectedItem().toString()));
                product.setCost(productPrice.getText().toString());
                product.setName(productName.getText().toString());
                productDatabase.createProduct(product);
                goToRecyclerViewActivity();
        }
    }

    private void goToRecyclerViewActivity(){
        Intent intent=new Intent(getActivity(), RecyclerViewActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null) {
                    searchOnMenFragment.setText(result.get(0));
                }
            }
        }
    }
}
