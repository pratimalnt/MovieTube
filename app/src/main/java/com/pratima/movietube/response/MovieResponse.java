package com.pratima.movietube.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pratima.movietube.model.Movie;

import java.util.List;

public class MovieResponse {
    /*
         "page": 1,
         "total_results": 10000,
         "total_pages": 500,
         "results": [
           {
             "popularity": 341.544,
             "vote_count": 2302,
             "video": false,
             "poster_path": "\/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
             "id": 419704,
             "adult": false,
             "backdrop_path": "\/5BwqwxMEjeFtdknRV792Svo0K1v.jpg",
             "original_language": "en",
             "original_title": "Ad Astra",
             "genre_ids": [
               12,
               18,
               9648,
               878,
               53
             ],
             "title": "Ad Astra",
             "vote_average": 6,
             "overview": "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.",
             "release_date": "2019-09-17"
           },
           {},
           {}......
           ]
        */
    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private String totalPages;

    //Json Array of movies data
    @SerializedName("results")
    @Expose
    private List<Movie> movieList = null;

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

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}