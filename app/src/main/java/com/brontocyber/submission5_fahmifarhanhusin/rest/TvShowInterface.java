package com.brontocyber.submission5_fahmifarhanhusin.rest;

import com.brontocyber.submission5_fahmifarhanhusin.model.RequestAcara;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvShowInterface {
    @GET("3/discover/tv?api_key="+ ApiViewModel.API_KEY)
    Call<RequestAcara> getTvShow(
            @Query("language") String bahasa
    );

    @GET("3/search/tv?api_key="+ApiViewModel.API_KEY)
    Call<RequestAcara> searchTv(
            @Query("language") String language,
            @Query("query") String query
    );


}
