package com.example.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.shopping.Ui.LoginFragment;
import com.example.shopping.Ui.SignUpFragment;
import com.example.shopping.ViewPager.PageAdapter;
import com.example.shopping.ViewPager.PageItems;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), getViewPagerItems());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
    private List<PageItems> getViewPagerItems() {
        List<PageItems> pageItems = new ArrayList<>();
        pageItems.add(new PageItems(getString(R.string.tab_1), new LoginFragment()));/////////
        pageItems.add(new PageItems(getString(R.string.tab_2), new SignUpFragment()));////////
        return pageItems;
    }
}