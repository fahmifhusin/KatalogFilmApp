package com.brontocyber.submission5_fahmifarhanhusin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestAcara {

    @SerializedName("page")
    String page;
    @SerializedName("total_results")
    String total_results;
    @SerializedName("total_pages")
    String total_pages;
    @SerializedName("results")
    @Expose
    List<Results> listAcara;
    @Expose
    String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public List<Results> getListAcara() {
        return listAcara;
    }

    public void setListAcara(List<Results> listAcara) {
        this.listAcara = listAcara;
    }
}
