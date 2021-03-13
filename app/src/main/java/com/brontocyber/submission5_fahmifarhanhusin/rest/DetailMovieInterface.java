package com.brontocyber.submission5_fahmifarhanhusin.rest;

import com.brontocyber.submission5_fahmifarhanhusin.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailMovieInterface {
    @GET("3/movie/{id_film}?api_key=d34a01dac0385df0afc746a68c7d8759")
    Call<Results>getAcaraDetail(
                @Path("id_film") String id,
                @Query("language") String bahasa
            );
}
