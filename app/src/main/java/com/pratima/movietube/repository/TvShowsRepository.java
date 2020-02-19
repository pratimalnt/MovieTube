package com.pratima.movietube.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pratima.movietube.api.ApiRequest;
import com.pratima.movietube.api.RetrofitRequest;
import com.pratima.movietube.response.TvShowsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsRepository {
    private static final String TAG = TvShowsRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public TvShowsRepository(){
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<TvShowsResponse> getPopularTvShows(String query, String key) {
        final MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();
        apiRequest.getPopularTvShows(query, key)
                .enqueue(new Callback<TvShowsResponse>() {


                    @Override
                    public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);


                        if (response.body() != null) {
                            data.setValue(response.body());
                            Log.d(TAG, "articles total result:: " + response.body().getTotalResults());
                            Log.d(TAG, "articles size:: " + response.body().getTvShowsList().size());
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShowsResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure response:: " + t.toString());
                        data.setValue(null);
                    }
                });
        return data;
    }
}
