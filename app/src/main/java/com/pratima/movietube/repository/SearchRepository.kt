package com.pratima.movietube.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratima.movietube.api.ApiRequest
import com.pratima.movietube.api.RetrofitRequest.retrofitInstance
import com.pratima.movietube.model.Movie
import com.pratima.movietube.model.TvShows
import com.pratima.movietube.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchRepository {

    private var apiRequest: ApiRequest? = null
    private val TAG = MovieRepository::class.java.simpleName

    init {
        apiRequest = retrofitInstance!!.create(ApiRequest::class.java)
    }

    fun getSearchResult(onNewData: OnNewData, query: String, key: String) {
        apiRequest!!.getMovieSearchResult(query, key)
            ?.enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>,
                    response: Response<MovieResponse?>
                ) {
                    Log.d(TAG, "onResponse response:: $response")
                    if (response.body() != null) {
                        onNewData.onSuccessSearchMovie(response.body()!!.movieList)
                    }
                }

                override fun onFailure(
                    call: Call<MovieResponse?>,
                    t: Throwable
                ) {
                    Log.d(
                        TAG,
                        "onFailure response:: $t"
                    )
                    onNewData.onFailure()
                }
            })
    }

    fun getPopularMovies(onNewData: OnNewData,
        query: String?,
        key: String?
    ): LiveData<MovieResponse?>? {
        val data = MutableLiveData<MovieResponse?>()
        apiRequest!!.getPopularMovies(query, key)
            ?.enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>,
                    response: Response<MovieResponse?>
                ) {
                    Log.d(TAG, "onResponse response:: $response")
                    if (response.body() != null) {
                        onNewData.onSuccessSearchMovie(response.body()!!.movieList)
                    }
                }

                override fun onFailure(
                    call: Call<MovieResponse?>,
                    t: Throwable
                ) {
                    Log.d(TAG, "onFailure response:: $t")
                    onNewData.onFailure()
                }
            })
        return data
    }

    interface OnNewData {
        fun onSuccessSearchMovie(data: List<Movie>)
        fun onSuccessSearchTV(data: List<TvShows>)
        fun onFailure()
    }
}