package com.brontocyber.submission5_fahmifarhanhusin.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MoviesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MoviesWidgetAdapter(this.getApplicationContext());
    }
}