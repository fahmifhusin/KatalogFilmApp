package com.brontocyber.submission5_fahmifarhanhusin.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.brontocyber.submission5_fahmifarhanhusin.R;

import java.util.Calendar;
import java.util.Date;


public class SettingsActivity extends AppCompatActivity {
    public static final int DAILY_CODE = 100;
    public static final int RELEASE_CODE = 101;
    public static final String ID_NOTIF = "notification";
    public static final String NOTIF_CHANNEL = "notification";
    private static final String STATS = "status";
    Toolbar toolbarSettings;
    Switch swDaily, swRelease;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbarSettings = findViewById(R.id.toolbar_settings);
        swDaily = findViewById(R.id.switch_daily_reminder);
        swRelease = findViewById(R.id.switch_release_today_reminder);
        setSupportActionBar(toolbarSettings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.settings));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Date currentTime = Calendar.getInstance().getTime();
        Log.d("waktu", String.valueOf(currentTime));
        showReminder();
    }

    private void showReminder() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swDaily.isChecked() == true) {
                    Toast.makeText(SettingsActivity.this, getResources().getString(R.string.daily_stats_enabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "IS Checked");
                    playReminderDaily(DAILY_CODE);
                    sharedPreferences.edit().putBoolean("checked", swDaily.isChecked()).apply();
                } else if (swDaily.isChecked() == false) {
                    Toast.makeText(SettingsActivity.this, getResources().getString(R.string.daily_stats_disabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "NOT Checked");
                    stopReminder(DAILY_CODE);
                    sharedPreferences.edit().putBoolean("checked", swDaily.isChecked()).apply();
                }
            }
        });

        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (swRelease.isChecked() == true) {
                    Toast.makeText(SettingsActivity.this, getResources().getString(R.string.release_stats_enabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "IS Checked");
                    playReminderDaily(RELEASE_CODE);
                    sharedPreferences.edit().putBoolean("checkedR", swRelease.isChecked()).apply();
                } else if (swRelease.isChecked() == false) {
                    Toast.makeText(SettingsActivity.this, getResources().getString(R.string.release_stats_disabled), Toast.LENGTH_SHORT).show();
                    Log.d(STATS, "NOT Checked");
                    stopReminder(RELEASE_CODE);
                    sharedPreferences.edit().putBoolean("checkedR", swRelease.isChecked()).apply();
                }
            }
        });
        swDaily.setChecked(false);
        swRelease.setChecked(false);
        stopReminder(DAILY_CODE);
        stopReminder(RELEASE_CODE);
        swDaily.setChecked(sharedPreferences.getBoolean("checked", false));
        swRelease.setChecked(sharedPreferences.getBoolean("checkedR", false));
    }

    private void playReminderDaily(int notifCode) {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = Calendar.getInstance().getTime();
        switch (notifCode) {
            case DAILY_CODE:
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Intent intent = new Intent(this, DailyReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, DAILY_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                break;
            case RELEASE_CODE:
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Intent intent2 = new Intent(this, StackReleaseReminder.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, RELEASE_CODE, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);
                break;
        }
    }

    private void stopReminder(int notifCode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        switch (notifCode) {
            case DAILY_CODE:
                Intent intent = new Intent(this, DailyReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, DAILY_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent.cancel();
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
                break;
            case RELEASE_CODE:
                Intent intent2 = new Intent(this, StackReleaseReminder.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, RELEASE_CODE, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent2.cancel();
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent2);
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
