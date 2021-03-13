package com.brontocyber.submission5_fahmifarhanhusin;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.brontocyber.submission5_fahmifarhanhusin.adapter.favorite.FavoritePageAdapter;

public class FavoriteActivity extends AppCompatActivity {
    Toolbar toolbarFav;
    TabLayout tabLayoutFav;
    TabItem favMovie, favTV;
    ViewPager viewPagerFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        toolbarFav = findViewById(R.id.toolbar);
        //membuat action bar menjadi toolbar
        setSupportActionBar(toolbarFav);
        //end
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.favorite));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        tabLayoutFav = findViewById(R.id.tab_favorite);
        favMovie = findViewById(R.id.movies_tab_favorite);
        favTV = findViewById(R.id.tv_tab_favorite);
        viewPagerFav = findViewById(R.id.viewPager_favorite);

        //memanggil page adapterTv
        FavoritePageAdapter pageAdapter = new FavoritePageAdapter(getSupportFragmentManager(), tabLayoutFav.getTabCount());
        //config adapterTv pada viewpager
        viewPagerFav.setAdapter(pageAdapter);
        //config tablayout pada pergantian fragment
        viewPagerFav.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutFav));
        tabLayoutFav.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerFav.setCurrentItem(tab.getPosition());
                Log.d("item selected", String.valueOf(tab.getText()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("item not selected", String.valueOf(tab.getText()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("item re selected", String.valueOf(tab.getText()));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }


}
