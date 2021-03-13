package com.brontocyber.submission5_fahmifarhanhusin.settings;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.brontocyber.submission5_fahmifarhanhusin.R;
import com.brontocyber.submission5_fahmifarhanhusin.model.RequestAcara;
import com.brontocyber.submission5_fahmifarhanhusin.model.Results;
import com.brontocyber.submission5_fahmifarhanhusin.rest.ApiViewModel;
import com.brontocyber.submission5_fahmifarhanhusin.rest.MoviesInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StackReleaseReminder extends BroadcastReceiver {
    private int idNotification = 0;
    private final List<NotificationItem> stackNotif = new ArrayList<>();

    private final static String GROUP_KEY = "group_key";
    private static final int MAX_NOTIFICATION = 5;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = format.format(calendar.getTime());

    @Override
    public void onReceive(final Context context, Intent intent) {
        MoviesInterface apiInterface = ApiViewModel.getClient().create(MoviesInterface.class);
        Call<RequestAcara> call = apiInterface.releaseToday(
                strDate, strDate
        );
        call.enqueue(new Callback<RequestAcara>() {
            @Override
            public void onResponse(Call<RequestAcara> call, Response<RequestAcara> response) {
                for (int i = 0; i < response.body().getListAcara().size(); i++) {
                    Results movie = response.body().getListAcara().get(i);
                    stackNotif.add(new NotificationItem(Integer.parseInt(movie.getId()),movie.getTitle()));
                    idNotification++;
                    Log.d("id", String.valueOf(idNotification));
                }
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder;
                for (int loop = 0; loop < idNotification; loop++){
                if (idNotification < MAX_NOTIFICATION) {
                    notificationBuilder = new NotificationCompat.Builder(context, SettingsActivity.ID_NOTIF)
                            .setSmallIcon(R.drawable.ic_new_releases_24dp)
                            .setContentTitle(context.getResources().getString(R.string.release_today))
                            .setContentText(String.valueOf(stackNotif.get(loop).getTitle()))
                            .setSound(defaultSoundUri)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                            .setAutoCancel(true);
                } else {
                    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                            .addLine(stackNotif.get(0).getTitle())
                            .addLine(stackNotif.get(1).getTitle())
                            .addLine(stackNotif.get(2).getTitle())
                            .addLine(stackNotif.get(3).getTitle())
                            .addLine(stackNotif.get(4).getTitle());
                    notificationBuilder = new NotificationCompat.Builder(context, SettingsActivity.ID_NOTIF)
                            .setContentTitle(context.getResources().getString(R.string.release_today))
                            .setContentText(String.valueOf(stackNotif.get(loop).getTitle()))
                            .setSmallIcon(R.drawable.ic_new_releases_24dp)
                            .setGroup(GROUP_KEY)
                            .setGroupSummary(true)
                            .setStyle(inboxStyle)
                            .setAutoCancel(true);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(SettingsActivity.ID_NOTIF, SettingsActivity.NOTIF_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
                    notificationBuilder.setChannelId(SettingsActivity.ID_NOTIF);
                    if (mNotificationManager != null) {
                        mNotificationManager.createNotificationChannel(channel);
                    }
                }
                if (idNotification < MAX_NOTIFICATION){
                    mNotificationManager.notify(SettingsActivity.RELEASE_CODE+loop, notificationBuilder.build());
                }else {
                    mNotificationManager.notify(SettingsActivity.RELEASE_CODE, notificationBuilder.build());
                    }
                }

            }

            @Override
            public void onFailure(Call<RequestAcara> call, Throwable t) {
                Log.d("status", "FAILED");
            }
        });

    }

}