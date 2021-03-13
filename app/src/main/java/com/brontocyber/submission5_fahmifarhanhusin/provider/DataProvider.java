package com.brontocyber.submission5_fahmifarhanhusin.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.FavTv;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;

import static com.brontocyber.submission5_fahmifarhanhusin.MainActivity.db;

public class DataProvider extends ContentProvider {
// data provider untuk fav movies
    public static final String AUTHORITY = "com.brontocyber.submission5_fahmifarhanhusin.provider";
    public static final Uri CONTENT_URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + "fav_movies");
    public static final Uri CONTENT_URI_TV = Uri.parse("content://" + AUTHORITY + "/" + "fav_tv");
    public static final int CODE_MENU_DIR = 1;
    public static final int CODE_MENU_ITEM = 2;
    public static final int CODE_TV_DIR = 3;
    public static final int CODE_TV_ITEM = 4;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private Context context;
    private Cursor cursorMovie;
    private Cursor cursorTv;
    static {
        MATCHER.addURI(AUTHORITY, "fav_movies", CODE_MENU_DIR);
        MATCHER.addURI(AUTHORITY, "fav_movies/*", CODE_MENU_ITEM);
        MATCHER.addURI(AUTHORITY, "fav_tv", CODE_TV_DIR);
        MATCHER.addURI(AUTHORITY, "fav_tv/*", CODE_TV_ITEM);
    }

    @Override
    public boolean onCreate() {
     return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,@Nullable String selection,
                        @Nullable String[] selectionArgs,@Nullable String sortOrder) {
        context = getContext();
        final int code = MATCHER.match(uri);
        switch (code){
            case CODE_MENU_DIR:
            case CODE_MENU_ITEM:
                cursorMovie = db.favDao().getMovie();
                cursorMovie.setNotificationUri(context.getContentResolver(), uri);
                return cursorMovie;
            case CODE_TV_DIR:
            case CODE_TV_ITEM:
                cursorTv = db.favTvDao().getTv();
                cursorTv.setNotificationUri(context.getContentResolver(), uri);
                return cursorTv;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull  Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                return "vnd.android.cursorMovie.dir/" + AUTHORITY +".fav_movies";
            case CODE_MENU_ITEM:
                return "vnd.android.cursorMovie.item/" + AUTHORITY +".fav_movies";
            case CODE_TV_DIR:
                return "vnd.android.cursorMovie.dir/" + AUTHORITY +".fav_tv";
            case CODE_TV_ITEM:
                return "vnd.android.cursorMovie.item/" + AUTHORITY +".fav_tv";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final Context context = getContext();
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                if (context == null) {
                    return null;
                }
                final long id = db.favDao().insertMovie(Favorite.moviesContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_MENU_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insertMovie with ID: " + uri);
            case CODE_TV_DIR:
                if (context == null) {
                    return null;
                }
                final long id2 = db.favTvDao().insertTv(FavTv.tvContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id2);
            case CODE_TV_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insertMovie with ID: " + uri);
                default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri,@Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final Context context = getContext();
        switch (MATCHER.match(uri)) {
            case CODE_MENU_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MENU_ITEM:
                if (context == null) {
                    return 0;
                }
                final int count = db.favDao().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            case CODE_TV_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_TV_ITEM:
                if (context == null) {
                    return 0;
                }
                final int count2 = db.favTvDao().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count2;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
