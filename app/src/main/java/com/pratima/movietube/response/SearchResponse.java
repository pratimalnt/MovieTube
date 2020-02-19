package com.pratima.movietube.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pratima.movietube.model.Search;

import java.util.List;

public class SearchResponse {
    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private String totalPages;

    @SerializedName("results")
    @Expose
    private List<Search> searchList = null;

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

    public List<Search> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Search> searchList) {
        this.searchList = searchList;
    }
}
