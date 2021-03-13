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
import com.brontocyber.submission5_fahmifarhanhusin.adapter.favorite.FavTvAdapter;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.FavTv;
import com.brontocyber.submission5_fahmifarhanhusin.provider.DataProvider;

import static com.brontocyber.submission5_fahmifarhanhusin.provider.DataProvider.CODE_TV_DIR;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvFragment extends Fragment {
    RecyclerView rv;
    FavTvAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_tv, container, false);
        //transfer data to adapterTv
        layoutManager = new LinearLayoutManager(getActivity());
        rv = view.findViewById(R.id.rv_fav_tv);
        rv.setLayoutManager(layoutManager);

        getActivity().getSupportLoaderManager().initLoader(CODE_TV_DIR, null, mLoaderCallbacks);
        return view;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case CODE_TV_DIR:
                            return new CursorLoader(getActivity().getApplicationContext(),
                                    DataProvider.CONTENT_URI_TV,
                                    new String[]{FavTv.COLUMN_TV_POSTER, FavTv.COLUMN_TV_NAME, FavTv.COLUMN_TV_RELEASE},
                                    null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()) {
                        case CODE_TV_DIR:
                            Log.d("cursor", String.valueOf(data.getCount()));
                            adapter = new FavTvAdapter(data);
                            rv.setAdapter(adapter);
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case CODE_TV_DIR:
                            break;
                    }
                }

            };



}
