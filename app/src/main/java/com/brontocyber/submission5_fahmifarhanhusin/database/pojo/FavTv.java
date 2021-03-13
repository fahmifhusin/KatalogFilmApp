package com.brontocyber.submission5_fahmifarhanhusin.database.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "fav_tv")
public class FavTv {

    public FavTv(){ }

    public static final String COLUMN_TV_ID = "id";
    public static final String COLUMN_TV_POSTER = "poster_path";
    public static final String COLUMN_TV_NAME = "name";
    public static final String COLUMN_TV_RELEASE = "first_air_date";

    public FavTv(Cursor cursor) {
        this.id = getColumnInt(cursor, COLUMN_TV_ID);
        this.poster_path = getColumnString(cursor,COLUMN_TV_POSTER);
        this.name = getColumnString(cursor, COLUMN_TV_NAME);
        this.release_date = getColumnString(cursor, COLUMN_TV_RELEASE);
    }

    public String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }



    public static FavTv tvContentValues(ContentValues values) {
        final FavTv movie = new FavTv();
        movie.id            = values.getAsInteger(COLUMN_TV_ID);
        movie.poster_path   = values.getAsString(COLUMN_TV_POSTER);
        movie.name         = values.getAsString(COLUMN_TV_NAME);
        movie.release_date  = values.getAsString(COLUMN_TV_RELEASE);
        return movie;
    }

    @ColumnInfo(name = "id")@PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name = "poster_path")@Nullable
    private String poster_path;

    @ColumnInfo(name = "name")@Nullable
    private String name;

    @ColumnInfo(name = "first_air_date")@Nullable
    private String release_date;

    public void setId(int id) {
        this.id = id;
    }

    public void setPoster_path(@Nullable String poster_path) {
        this.poster_path = poster_path;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public void setRelease_date(@Nullable String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getName() {
        return name;
    }

    public String getRelease_date() {
        return release_date;
    }
}
