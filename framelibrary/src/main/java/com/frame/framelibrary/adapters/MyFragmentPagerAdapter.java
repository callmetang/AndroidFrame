package com.frame.framelibrary.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by a on 2018/10/26.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    private List<String> titles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            String title = titles.get(position);
            return TextUtils.isEmpty(title) ? "" : title;
        }
        return String.valueOf(position);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
//        fragment.setUserVisibleHint(true);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
