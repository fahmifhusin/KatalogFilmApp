package com.brontocyber.submission5_fahmifarhanhusin.adapter.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brontocyber.submission5_fahmifarhanhusin.fragment.main.MoviesFragment;
import com.brontocyber.submission5_fahmifarhanhusin.fragment.main.TvShowFragment;

public class PageAdapter extends FragmentPagerAdapter {


    private int numOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MoviesFragment();
            case 1:
                return new TvShowFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
