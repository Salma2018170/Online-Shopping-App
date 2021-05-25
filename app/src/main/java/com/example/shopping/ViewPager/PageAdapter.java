package com.example.shopping.ViewPager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class PageAdapter extends FragmentStatePagerAdapter {
    private List<PageItems> pageItems;

    public PageAdapter(FragmentManager fragmentManager, List<PageItems> pageItems)
    {
        super(fragmentManager);
        this.pageItems = pageItems;

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pageItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return pageItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageItems.get(position).getName();
    }
}
