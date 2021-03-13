package com.brontocyber.submission5_fahmifarhanhusin.database.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

//entity = column
@Entity(tableName = "fav_movies")
public class Favorite {

    public Favorite(){ }

    public static final String COLUMN_MOVIES_ID = "id";
    public static final String COLUMN_MOVIES_POSTER = "poster_path";
    public static final String COLUMN_MOVIES_TITLE = "title";
    public static final String COLUMN_MOVIES_RELEASE = "release_date";

    public Favorite(Cursor cursor) {
        this.id = getColumnInt(cursor, COLUMN_MOVIES_ID);
        this.poster_path = getColumnString(cursor,COLUMN_MOVIES_POSTER);
        this.title = getColumnString(cursor, COLUMN_MOVIES_TITLE);
        this.release_date = getColumnString(cursor, COLUMN_MOVIES_RELEASE);
    }

    public String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static Favorite moviesContentValues(ContentValues values) {
        final Favorite movie = new Favorite();
            movie.id            = values.getAsInteger(COLUMN_MOVIES_ID);
            movie.poster_path   = values.getAsString(COLUMN_MOVIES_POSTER);
            movie.title         = values.getAsString(COLUMN_MOVIES_TITLE);
            movie.release_date  = values.getAsString(COLUMN_MOVIES_RELEASE);
        return movie;
    }

    @ColumnInfo(name = "id")@PrimaryKey @NonNull
    private int id;

    @ColumnInfo(name = "poster_path")@Nullable
    private String poster_path;

    @ColumnInfo(name = "title")@Nullable
    private String title;

    @ColumnInfo(name = "release_date")@Nullable
    private String release_date;

    public void setId(int id) {
        this.id = id;
    }

    public void setPoster_path(@Nullable String poster_path) {
        this.poster_path = poster_path;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }
}
