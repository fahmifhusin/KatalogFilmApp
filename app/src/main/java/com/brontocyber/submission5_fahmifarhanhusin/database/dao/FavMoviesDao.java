package com.brontocyber.submission5_fahmifarhanhusin.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;

@Dao
public interface FavMoviesDao {

    //set provider DAO
    @Query("SELECT * FROM fav_movies")
    Cursor getMovie();

    @Insert
    long insertMovie(Favorite favorite);

    @Query("DELETE FROM fav_movies WHERE "+ Favorite.COLUMN_MOVIES_ID +" = :id")
    int deleteById(long id);


}
