package com.brontocyber.submission5_fahmifarhanhusin.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.FavTv;

import java.util.List;
@Dao
public interface FavTvDao {

    //set provider DAO
    @Query("SELECT * FROM fav_tv")
    Cursor getTv();

    @Insert
    long insertTv(FavTv favorite);

    @Query("DELETE FROM fav_tv WHERE "+ FavTv.COLUMN_TV_ID +" = :id")
    int deleteById(long id);


}
