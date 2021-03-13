package com.brontocyber.submission5_fahmifarhanhusin.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.brontocyber.submission5_fahmifarhanhusin.DetailMovieActivity;
import com.brontocyber.submission5_fahmifarhanhusin.R;

/**
 * Implementation of App Widget functionality.
 */
public class FavMoviesWidget extends AppWidgetProvider {
    private static final String TOAST_ACTION = "com.brontocyber.submission5_fahmifarhanhusin.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.brontocyber.submission5_fahmifarhanhusin.EXTRA_ITEM";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, MoviesWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fav_movies_widget);
        views.setRemoteAdapter(R.id.stack_movies, intent);
        views.setEmptyView(R.id.stack_movies, R.id.empty_movies);

        Intent toastIntent = new Intent(context, FavMoviesWidget.class);
        toastIntent.setAction(FavMoviesWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_movies, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Log.d("data", String.valueOf(viewIndex));
                Intent detailAcara = new Intent(context, DetailMovieActivity.class);
                String idParseString = String.valueOf(viewIndex);
                detailAcara.putExtra(DetailMovieActivity.idAcara,idParseString);
                detailAcara.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detailAcara);
            }
        }
    }
}

