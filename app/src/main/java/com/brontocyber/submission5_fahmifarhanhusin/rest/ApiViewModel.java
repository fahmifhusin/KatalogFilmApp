package com.brontocyber.submission5_fahmifarhanhusin.rest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.brontocyber.submission5_fahmifarhanhusin.DetailMovieActivity;
import com.brontocyber.submission5_fahmifarhanhusin.DetailTvActivity;
import com.brontocyber.submission5_fahmifarhanhusin.fragment.main.MoviesFragment;
import com.brontocyber.submission5_fahmifarhanhusin.fragment.main.TvShowFragment;
import com.brontocyber.submission5_fahmifarhanhusin.model.Results;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiViewModel extends ViewModel {

    //set url server & key
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String API_KEY = "d34a01dac0385df0afc746a68c7d8759";
    public static final String IMAGE_URL ="https://image.tmdb.org/t/p/w342";
    private static Retrofit retrofit = null;
    //handle connection
    public static Retrofit getClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

//    Async viewmodel
    public static MutableLiveData<List<Results>> ACARA_MOVIE = null;
    public LiveData<List<Results>> getMovieCache() {
        if (ACARA_MOVIE == null) {
            ACARA_MOVIE = new MutableLiveData<>();
            loadAcara();
        }
        return ACARA_MOVIE;
    }

    public static MutableLiveData<List<Results>> ACARA_TV = null;
    public LiveData<List<Results>> getTvShowCache(){
        if (ACARA_TV == null) {
            ACARA_TV = new MutableLiveData<>();
            loadTv();
        }
        return ACARA_TV;
    }

    public static MutableLiveData<Results> ACARA_DETAIL = null;
    public LiveData<Results> getDetailMovieCache(){
        if (ACARA_DETAIL == null) {
            ACARA_DETAIL = new MutableLiveData<>();
            loadDetailMovie();
        }
        return ACARA_DETAIL;
    }

    public static MutableLiveData<Results> ACARA_SHOW = null;
    public LiveData<Results> getDetailTvCache(){
        if (ACARA_SHOW == null) {
            ACARA_SHOW = new MutableLiveData<>();
            loadDetailTv();
        }
        return ACARA_SHOW;
    }

    //get data JSON data dari fragment
    private void loadAcara() {
        MoviesFragment dataMovies = new MoviesFragment();
        dataMovies.loadMovies();
    }

    private void loadTv(){
        TvShowFragment dataShow = new TvShowFragment();
        dataShow.loadTvShow();
    }

    private void loadDetailMovie(){
        DetailMovieActivity dataMovie = new DetailMovieActivity();
        dataMovie.loadDetail();
    }

    private void loadDetailTv(){
        DetailTvActivity dataTv = new DetailTvActivity();
        dataTv.loadDetail();
    }




}
