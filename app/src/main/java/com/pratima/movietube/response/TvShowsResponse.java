package com.pratima.movietube.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pratima.movietube.model.Movie;
import com.pratima.movietube.model.TvShows;

import java.util.List;

public class TvShowsResponse {

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private String totalPages;

    //Json Array of tvShows data
    @SerializedName("results")
    @Expose
    private List<TvShows> tvShowsList = null;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<TvShows> getTvShowsList() {
        return tvShowsList;
    }

    public void setTvShowsList(List<TvShows> tvShowsList) {
        this.tvShowsList = tvShowsList;
    }

}
