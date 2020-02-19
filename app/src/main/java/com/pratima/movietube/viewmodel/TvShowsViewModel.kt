package com.pratima.movietube.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pratima.movietube.api.ApiConstants
import com.pratima.movietube.repository.TvShowsRepository
import com.pratima.movietube.response.TvShowsResponse

class TvShowsViewModel(application: Application) : AndroidViewModel(application) {
    val popularTvShowsData: LiveData<TvShowsResponse>

    init {
        val tvShowsRepository = TvShowsRepository()

        popularTvShowsData = tvShowsRepository.getPopularTvShows(
            ApiConstants.POPULAR_DESC,
            ApiConstants.API_KEY
        )
    }
}