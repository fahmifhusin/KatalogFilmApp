package com.brontocyber.submission5_fahmifarhanhusin.fragment.favorite;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brontocyber.submission5_fahmifarhanhusin.R;
import com.brontocyber.submission5_fahmifarhanhusin.adapter.favorite.FavMovieAdapter;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;
import com.brontocyber.submission5_fahmifarhanhusin.provider.DataProvider;

import static com.brontocyber.submission5_fahmifarhanhusin.provider.DataProvider.CODE_MENU_DIR;

public class FavMovieFragment extends Fragment {
    RecyclerView rv;
    FavMovieAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        //transfer data to adapterTv
        layoutManager = new LinearLayoutManager(getActivity());
            rv = view.findViewById(R.id.rv_fav_movies);
            rv.setLayoutManager(layoutManager);

        getActivity().getSupportLoaderManager().initLoader(CODE_MENU_DIR, null, mLoaderCallbacks);
        return view;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case CODE_MENU_DIR:
                            return new CursorLoader(getActivity().getApplicationContext(),
                                    DataProvider.CONTENT_URI_MOVIE,
                                    new String[]{Favorite.COLUMN_MOVIES_POSTER,Favorite.COLUMN_MOVIES_TITLE, Favorite.COLUMN_MOVIES_RELEASE},
                                    null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()) {
                        case CODE_MENU_DIR:
                            adapter = new FavMovieAdapter(data);
                            rv.setAdapter(adapter);
                            Log.d("cursor", String.valueOf(data.getCount()));
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case CODE_MENU_DIR:

                             break;
                    }
                }

            };



}
