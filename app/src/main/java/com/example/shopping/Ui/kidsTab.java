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
public class kidsTab extends Fragment implements RecyclerViewEvent, View.OnClickListener {
    private ImageView productImg;
    private String searchedText;
    private ArrayList<Model> filteredList;
    private Intent intentedData;
    private View view;
    private RecyclerView myRecyclerView;
    private List<Model> itemList;
    private Dialog mDialog;
    private final int VOICE_REQUEST = 1399;
    private Button voiceOnKidFragment;
    private Product product;
    private TextView productName ;
    private TextView productPrice ;
    private Spinner productQuantities;
    private Button addToCart;
    private Database productDatabase;
    private EditText searchOnKidFragment;
    private CategoryAdapter categoryAdapter;
    private Model actualModel;

    public kidsTab() {
        // Required empty public constructor
    }

    public static kidsTab newInstance() {
        return new kidsTab();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_kids_tab,container,false);
        linkKidFragmentXmlWithJava();
        categoryAdapter =new CategoryAdapter(getContext(),itemList,this);
        setListAndAdapter();
        productDatabase=new Database(getContext());
        searchOnKidFragment.addTextChangedListener(new TextWatcher() {
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

    private void linkKidFragmentXmlWithJava(){
        myRecyclerView = view.findViewById(R.id.recycler_kids_tabs);
        searchOnKidFragment=view.findViewById(R.id.search3);
        voiceOnKidFragment=view.findViewById(R.id.record3);
        voiceOnKidFragment.setOnClickListener(this);
    }

    private void setListAndAdapter(){
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addModels();
    }

    private void addModels() {
        itemList = new ArrayList<>();
        itemList.add(new Model("KidJacket","25$",R.drawable.jacket_kids));
        itemList.add(new Model("KidJeans","20$",R.drawable.jeans_kids));
        itemList.add(new Model("KidT_Shirt","15$",R.drawable.tshirt_kids));
        itemList.add(new Model("JumpSuit","15$",R.drawable.sloubet_kids));
    }


    @Override
    public void onRecyclerViewClick(final Model model) {
        actualModel=model;
        mDialog = new Dialog(getActivity());
        mDialog.setContentView(R.layout.dialog_order);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        linkDialogXmlWithJava();

        setDialogValues();


        mDialog.show();
    }

    private void linkDialogXmlWithJava(){
        productName = mDialog.findViewById(R.id.product_name);
        productPrice = mDialog.findViewById(R.id.product_cost);
        productImg = mDialog.findViewById(R.id.product_img);
        productQuantities=mDialog.findViewById(R.id.spinner);
        addToCart=mDialog.findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(this);

    }

    private void setDialogValues(){
        productName.setText(actualModel.getTitle());
        productPrice.setText(actualModel.getPrice());
        productImg.setImageResource(actualModel.getImg());
    }

    public void filter(String text) {
        searchedText=text;
        searchForModelByText();
        categoryAdapter.filterList(filteredList);
    }

    private void searchForModelByText(){
        filteredList = new ArrayList<>();
        for (Model items : itemList) {
            if (items.getTitle().toLowerCase().contains(searchedText.toLowerCase())) {
                filteredList.add(items);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record3:
                getSpeechOfVoice();
                break;
            case R.id.add_to_cart:
                setProductDatabase();
                productDatabase.createProduct(product);
                goToRecyclerViewActivity();
                break;
        }
    }

    private void getSpeechOfVoice(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startActivityForResult(intent, VOICE_REQUEST);
    }


    private void setProductDatabase(){
        product=new Product();
        product.setQuantities(Integer.parseInt(productQuantities.getSelectedItem().toString()));
        product.setCost(productPrice.getText().toString());
        product.setName(productName.getText().toString());
    }

    private void goToRecyclerViewActivity(){
        Intent intent=new Intent(getActivity(), RecyclerViewActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intentedData=data;
        if (requestCode == VOICE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                getSpeechAndCheckIfExist();
            }
        }
    }

    private void getSpeechAndCheckIfExist(){
        ArrayList<String> result = intentedData.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        if (result != null) {
            searchOnKidFragment.setText(result.get(0));
        }
    }
}
