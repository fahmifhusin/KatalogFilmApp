package com.brontocyber.submission5_fahmifarhanhusin.model;


import com.google.gson.annotations.SerializedName;


public class Results {
   @SerializedName("id")
    private String id;
   @SerializedName("overview")
    private String overview;

    //variabel TV Show
   @SerializedName("name")
   private String name;
   @SerializedName("first_air_date")
   private String first_air_date;

   //variabel Movies
   @SerializedName("poster_path")
    private String poster_path;
   @SerializedName("title")
    private String title;
   @SerializedName("release_date")
    private String release_date;

    public Results(String poster_path, String title, String release_date,String overview) {
        this.overview = overview;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
    }

    public Results() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
