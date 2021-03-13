package com.brontocyber.submission5_fahmifarhanhusin.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class TvWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TvWidgetAdapter(this.getApplicationContext());
    }
}