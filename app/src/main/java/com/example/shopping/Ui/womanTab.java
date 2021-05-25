package com.example.shopping.Ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.RecyclerViewActivity;
import com.example.shopping.ViewPager.CategoryAdapter;
import com.example.shopping.ViewPager.RecyclerViewEvent;
import com.example.shopping.storage.Product;
import com.example.shopping.storage.Database;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class womanTab extends Fragment implements RecyclerViewEvent, View.OnClickListener {
    private RecyclerView myRecyclerView;
    private List<Model> itemList;
    private Dialog mDialog;
    private final int VOICE_REQUEST = 1399;
    Button voiceOnWomenFragment;
    Product product;
    TextView productName ;
    TextView productPrice ;
    Button addToCart;
    String COST;
    int QUANTITIES;
    String NAME;
    Spinner productQuantities;
    Database productDatabase;
    EditText searchOnWomenFragment;
    private CategoryAdapter categoryAdapter;
    public womanTab() {
        // Required empty public constructor
    }

    public static womanTab newInstance() {
        return new womanTab();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_woman_tab, container, false);
        myRecyclerView = v.findViewById(R.id.recycler_women_tab);
        categoryAdapter = new CategoryAdapter(getContext(), itemList, this);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerView.setAdapter(categoryAdapter);
        productDatabase=new Database(getContext());

        searchOnWomenFragment=v.findViewById(R.id.search2);
        voiceOnWomenFragment=v.findViewById(R.id.record2);
        voiceOnWomenFragment.setOnClickListener(this);
        searchOnWomenFragment.addTextChangedListener(new TextWatcher() {
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

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemList = new ArrayList<>();
        itemList.add(new Model("WomenJacket", "20$", R.drawable.jacket));
        itemList.add(new Model("WomenJeans", "21$", R.drawable.jeans));
        itemList.add(new Model("Dress", "25$", R.drawable.dress));
        itemList.add(new Model("Blouse", "15$", R.drawable.blouse));
        itemList.add(new Model("WomenT_Shirt", "15$", R.drawable.t_shirt));
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
        if (view.getId() == R.id.record2) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, VOICE_REQUEST);
        }
        else if(view.getId()==R.id.add_to_cart){
            product=new Product();
            product.setQuantities(Integer.parseInt(productQuantities.getSelectedItem().toString()));
            product.setCost(productPrice.getText().toString());
            product.setName(productName.getText().toString());
//            COST=productPrice.getText().toString();
//            NAME=productName.getText().toString();
//            QUANTITIES=Integer.parseInt(productQuantities.getSelectedItem().toString());
//            product=new Product(NAME,COST,QUANTITIES);
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
                    searchOnWomenFragment.setText(result.get(0));
                }
            }
        }
    }
}
