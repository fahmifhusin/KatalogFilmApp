package com.brontocyber.submission5_fahmifarhanhusin.adapter.favorite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brontocyber.submission5_fahmifarhanhusin.fragment.favorite.FavMovieFragment;
import com.brontocyber.submission5_fahmifarhanhusin.fragment.favorite.FavTvFragment;

public class FavoritePageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public FavoritePageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FavMovieFragment();
            case 1:
                return new FavTvFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


}
