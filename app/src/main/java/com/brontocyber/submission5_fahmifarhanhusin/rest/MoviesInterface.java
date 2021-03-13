package com.brontocyber.submission5_fahmifarhanhusin.rest;

import com.brontocyber.submission5_fahmifarhanhusin.model.RequestAcara;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoviesInterface {

        @GET("3/discover/movie?api_key="+ ApiViewModel.API_KEY)
    Call<RequestAcara> getMovies(
                @Query("language") String bahasa
        );

    @GET("3/search/movie?api_key="+ApiViewModel.API_KEY)
    Call<RequestAcara> searchMovie(
            @Query("language") String language,
            @Query("query") String query
    );

    @GET("3/discover/movie?api_key="+ApiViewModel.API_KEY)
    Call<RequestAcara> releaseToday(
            @Query("primary_release_date.gte") String release_gte,
            @Query("primary_release_date.lte") String release_lte
            );


}
