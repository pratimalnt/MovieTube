package com.pratima.movietube.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratima.movietube.api.ApiConstants
import com.pratima.movietube.model.DataModel
import com.pratima.movietube.repository.DataRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    var popularMovieLiveData: LiveData<DataModel>?
    private val TAG: String = "SearchViewModel"
    private var searchString = MutableLiveData<String>()
    private var searchData: LiveData<DataModel>
    var searchResponse: MutableLiveData<List<DataModel>>

    init {
        searchString.value = null
        searchData = MutableLiveData()
        searchResponse = MutableLiveData()
        searchResponse.value = null
        Log.d(TAG, "searchData init::$searchData")

        val movieRepository = DataRepository()
        popularMovieLiveData = movieRepository.getPopularMovies(
            ApiConstants.POPULAR_DESC,
            ApiConstants.API_KEY

        )
    }

    fun setSearchQuery(query: String) {
        val searchRepository = DataRepository()
        if (query.isNotEmpty()) {
            searchRepository.getSearchResult(object : DataRepository.OnNewData {
                override fun onSuccessSearchMovie(data: List<DataModel>) {
                    searchResponse.value = data
                }
                override fun onFailure() {
                    //REQUEST FAILED
                }

            }, query, ApiConstants.API_KEY)
        }

    }
}