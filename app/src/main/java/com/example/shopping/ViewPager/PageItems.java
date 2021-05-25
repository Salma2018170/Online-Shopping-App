package com.example.shopping.ViewPager;

import androidx.fragment.app.Fragment;

public class PageItems {
    private String name;
    private Fragment fragment;

    public PageItems(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public Fragment getFragment() {
        return fragment;
    }
}