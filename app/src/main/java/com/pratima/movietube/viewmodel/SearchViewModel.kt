package com.pratima.movietube.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratima.movietube.api.ApiConstants
import com.pratima.movietube.model.Movie
import com.pratima.movietube.model.TvShows
import com.pratima.movietube.repository.MovieRepository
import com.pratima.movietube.repository.SearchRepository
import com.pratima.movietube.response.MovieResponse

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String = "SearchViewModel"
    private var searchString = MutableLiveData<String>()
    private var searchData: LiveData<MovieResponse>
    var searchResponse: MutableLiveData<List<Movie>>

    init {
        searchString.value = null
        searchData = MutableLiveData()
        searchResponse = MutableLiveData()
        searchResponse.value = null
        Log.d(TAG, "searchData init::$searchData")

    }

    fun setSearchQuery(query: String) {
        val searchRepository = SearchRepository()
        if (query.isNotEmpty()) {
            searchRepository.getSearchResult(object : SearchRepository.OnNewData {
                override fun onSuccessSearchMovie(data: List<Movie>) {
                    searchResponse.value = data
                }
                override fun onFailure() {
                    //REQUEST FAILED
                }

                override fun onSuccessSearchTV(data: List<TvShows>) {

                }
            }, query, ApiConstants.API_KEY)
        }

    }
}