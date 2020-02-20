package com.pratima.movietube.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratima.movietube.api.ApiRequest
import com.pratima.movietube.api.RetrofitRequest
import com.pratima.movietube.model.DataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository {

    private var apiRequest: ApiRequest? = null
    private val TAG = DataRepository::class.java.simpleName

    init {
        apiRequest = RetrofitRequest.retrofitInstance!!.create(ApiRequest::class.java)
    }

    fun getPopularMovies(query: String, key: String): LiveData<DataModel> {
        val data = MutableLiveData<DataModel>()
        apiRequest?.getPopularMovies(query, key)!!
            .enqueue(object : Callback<DataModel?> {
                override fun onResponse(call: Call<DataModel?>, response: Response<DataModel?>) {
                    Log.d(TAG, "onResponse response for Movie:: $response")


                    if (response.body() != null) {
                        data.setValue(response.body())
                        Log.d(TAG, "articles total result:: " + response.body()!!.total_results)
                        Log.d(TAG, "articles size:: " + response.body()!!.results.size)
                    }
                }
                override fun onFailure(call: Call<DataModel?>, t: Throwable) {
                    Log.d(TAG, "onFailure response:: $t")
                    data.setValue(null)
                }
            })
        return data
    }

    fun getPopularTvShows(query: String, key: String): LiveData<DataModel> {
        val data = MutableLiveData<DataModel>()
        apiRequest?.getPopularTvShows(query, key)!!
            .enqueue(object : Callback<DataModel?> {
                override fun onResponse(call: Call<DataModel?>, response: Response<DataModel?>) {
                    Log.d(TAG, "onResponse response for Movie:: $response")


                    if (response.body() != null) {
                        data.setValue(response.body())
                        Log.d(TAG, "articles total result:: " + response.body()!!.total_results)
                        Log.d(TAG, "articles size:: " + response.body()!!.results.size)
                    }
                }
                override fun onFailure(call: Call<DataModel?>, t: Throwable) {
                    Log.d(TAG, "onFailure response:: $t")
                    data.setValue(null)
                }
            })
        return data
    }

    fun getSearchResult(onNewData: OnNewData, query: String, key: String) {
        apiRequest!!.getMovieSearchResult(query, key)
            ?.enqueue(object : Callback<DataModel?> {
                override fun onResponse(
                    call: Call<DataModel?>,
                    response: Response<DataModel?>
                ) {
                    Log.d(TAG, "onResponse response:: $response")
                    if (response.body() != null) {
                        onNewData.onSuccessSearchMovie(response.body()!!.results)
                    }
                }

                override fun onFailure(
                    call: Call<DataModel?>,
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

    interface OnNewData {
        fun onSuccessSearchMovie(data: List<DataModel>)
        fun onFailure()
    }
}