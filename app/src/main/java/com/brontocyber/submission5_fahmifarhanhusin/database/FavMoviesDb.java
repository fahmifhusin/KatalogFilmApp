package com.brontocyber.submission5_fahmifarhanhusin.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.brontocyber.submission5_fahmifarhanhusin.database.dao.FavMoviesDao;
import com.brontocyber.submission5_fahmifarhanhusin.database.dao.FavTvDao;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.FavTv;
import com.brontocyber.submission5_fahmifarhanhusin.database.pojo.Favorite;

@Database(entities={Favorite.class, FavTv.class},version = 1,  exportSchema = true)

public abstract class FavMoviesDb extends RoomDatabase{
    public abstract FavMoviesDao favDao();
    public abstract FavTvDao favTvDao();
}
