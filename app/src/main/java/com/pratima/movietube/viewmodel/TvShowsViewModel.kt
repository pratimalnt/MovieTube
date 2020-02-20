package com.pratima.movietube.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pratima.movietube.api.ApiConstants
import com.pratima.movietube.model.DataModel
import com.pratima.movietube.repository.DataRepository

class TvShowsViewModel(application: Application) : AndroidViewModel(application) {
    val popularTvShowsData: LiveData<DataModel>

    init {
        val tvShowsRepository = DataRepository()

        popularTvShowsData = tvShowsRepository.getPopularTvShows(
            ApiConstants.POPULAR_DESC,
            ApiConstants.API_KEY
        )
    }
}