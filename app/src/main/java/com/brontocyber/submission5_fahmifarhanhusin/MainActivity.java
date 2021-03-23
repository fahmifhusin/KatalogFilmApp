package com.brontocyber.submission5_fahmifarhanhusin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.brontocyber.submission5_fahmifarhanhusin.adapter.main.PageAdapter;
import com.brontocyber.submission5_fahmifarhanhusin.database.FavMoviesDb;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;
import com.brontocyber.submission5_fahmifarhanhusin.fragment.main.MoviesFragment;
import com.brontocyber.submission5_fahmifarhanhusin.fragment.main.TvShowFragment;
import com.brontocyber.submission5_fahmifarhanhusin.settings.SettingsActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{

    //view
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabItem tabMovie, tabTV;
    private ViewPager viewPager;
    private SearchView searchView;
    public static FavMoviesDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mendefinisikan variabel
        toolbar = findViewById(R.id.toolbar);
        //membuat action bar menjadi toolbar
        setSupportActionBar(toolbar);
        //end
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.daftar_film));
        }
        tabLayout = findViewById(R.id.tablayout);
        tabMovie = findViewById(R.id.movies_tab);
        tabTV = findViewById(R.id.tv_tab);
        viewPager = findViewById(R.id.viewPager);
        //room db
        db = Room.databaseBuilder(this.getApplicationContext(),
        FavMoviesDb.class, "acara").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        //memanggil page adapterTv
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //config adapterTv pada viewpager
        viewPager.setAdapter(pageAdapter);
        //config tablayout pada pergantian fragment
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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

    //override method untuk menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //kode untuk search
        MenuItem item = menu.findItem(R.id.search_acara);
        MenuItem favItem = menu.findItem(R.id.fav_item);
        favItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                return true;
            }
        });
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Cari Acara");
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_change_settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                Log.d("bahasa",Locale.getDefault().getCountry());
                startActivity(mIntent);
                break;
            case R.id.fav_item:
                Intent mFavIntent = new Intent(this, FavoriteActivity.class);
                startActivity(mFavIntent);
                break;
            case R.id.notifications_settings:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //override method searchview
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String cari) {
    MoviesFragment movie = new MoviesFragment();
    TvShowFragment tvShow = new TvShowFragment();
    movie.searchDataMovie(cari);
    tvShow.searchDataTv(cari);
        return true;
    }
}
