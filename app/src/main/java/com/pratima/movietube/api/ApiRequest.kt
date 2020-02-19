package com.pratima.movietube.api

import com.pratima.movietube.response.MovieResponse
import com.pratima.movietube.response.SearchResponse
import com.pratima.movietube.response.TvShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface ApiRequest {

    @GET("3/discover/movie/")
    fun getPopularMovies(
        @Query("page") query: String?,
        @Query("api_key") apiKey: String?
    ): Call<MovieResponse?>?

    @GET("3/discover/tv/")
    fun getPopularTvShows(
        @Query("sort_by") query: String?,
        @Query("api_key") apiKey: String?
    ): Call<TvShowsResponse?>?

    @GET("3/search/movie/")
    fun getMovieSearchResult(
        @Query("query") query: String?,
        @Query("api_key") apiKey: String?
    ): Call<MovieResponse?>?
}