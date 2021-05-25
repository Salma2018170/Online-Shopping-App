package com.example.shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shopping.Ui.ManTab;
import com.example.shopping.Ui.kidsTab;
import com.example.shopping.Ui.womanTab;
import com.example.shopping.ViewPager.PageAdapter;
import com.example.shopping.ViewPager.PageItems;
import com.example.shopping.storage.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity  {
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            int signUpId=bundle.getInt("Id");
            System.out.println(signUpId);
            SharedPreferences sharedPreferences=getSharedPreferences(Constants.PREFERENCE_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt(Constants.LoginTable.ID,signUpId);
            editor.putBoolean(Constants.LoginTable.REMEMBER_ME,false);
            editor.apply();
            Toast.makeText(this, String.valueOf(sharedPreferences.getInt(Constants.LoginTable.ID,0)), Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.PREFERENCE_NAME,Context.MODE_PRIVATE);
        id=sharedPreferences.getInt(Constants.LoginTable.ID,0);
        Toast.makeText(this, String.valueOf(sharedPreferences.getInt(Constants.LoginTable.ID,0)), Toast.LENGTH_SHORT).show();
        //fragment
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),getViewPagerItem());
        TabLayout tabLayout = findViewById(R.id.tableLayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private List<PageItems> getViewPagerItem() {
        List<PageItems> pageItems = new ArrayList<>();
        pageItems.add(new PageItems(getString(R.string.men_tab),ManTab.newInstance()));
        pageItems.add(new PageItems(getString(R.string.women_tab), womanTab.newInstance()));
        pageItems.add(new PageItems(getString(R.string.kids_tab), kidsTab.newInstance()));
        return pageItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.shopping_cart){
            goToShoppingCart();
            return true;
        }
        return false;
    }

    private void goToShoppingCart(){
        Intent intent=new Intent(RecyclerViewActivity.this,ShoppingCart.class);
        startActivity(intent);
    }

}