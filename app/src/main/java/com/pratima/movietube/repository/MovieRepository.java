package com.pratima.movietube.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pratima.movietube.api.ApiRequest;
import com.pratima.movietube.api.RetrofitRequest;
import com.pratima.movietube.response.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public MovieRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<MovieResponse> getPopularMovies(String query, String key) {
        final MutableLiveData<MovieResponse> data = new MutableLiveData<>();
        apiRequest.getPopularMovies(query, key)
                .enqueue(new Callback<MovieResponse>() {


                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            data.setValue(response.body());
                            Log.d(TAG, "articles total result:: " + response.body().getTotalResults());
                            Log.d(TAG, "articles size:: " + response.body().getMovieList().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure response:: " + t.toString());
                        data.setValue(null);
                    }
                });
        return data;
    }
}
