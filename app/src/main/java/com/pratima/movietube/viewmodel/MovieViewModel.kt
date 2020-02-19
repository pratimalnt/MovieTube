package com.pratima.movietube.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pratima.movietube.api.ApiConstants
import com.pratima.movietube.repository.MovieRepository
import com.pratima.movietube.response.MovieResponse

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    var popularMovieLiveData: LiveData<MovieResponse>?

    init {
        val movieRepository = MovieRepository()
        popularMovieLiveData = movieRepository.getPopularMovies(
            ApiConstants.POPULAR_DESC,
            ApiConstants.API_KEY

        )
    }
}