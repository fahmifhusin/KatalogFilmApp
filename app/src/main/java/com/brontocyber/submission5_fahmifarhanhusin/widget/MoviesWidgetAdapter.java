package com.brontocyber.submission5_fahmifarhanhusin.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.brontocyber.submission5_fahmifarhanhusin.R;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;
import com.brontocyber.submission5_fahmifarhanhusin.provider.DataProvider;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MoviesWidgetAdapter implements RemoteViewsService.RemoteViewsFactory {
    private Cursor list;
    private final Context mContext;


    MoviesWidgetAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        try{
        list = mContext.getContentResolver().query(
                DataProvider.CONTENT_URI_MOVIE,
                null,
                null,
                null,
                null
        );
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Favorite item = getItem(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(ApiViewModel.IMAGE_URL + item.getPoster_path())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rv.setImageViewBitmap(R.id.image_item, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavMoviesWidget.EXTRA_ITEM, item.getId());
        Intent intent = new Intent();
        intent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.image_item, intent);
        return rv;
    }

    private Favorite getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new Favorite(list);
    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDataSetChanged() { }

    @Override
    public void onDestroy() {
        //required
    }

}
