package com.pratima.movietube.view.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pratima.movietube.R;
import com.pratima.movietube.api.ApiConstants;
import com.pratima.movietube.model.DataModel;
import com.pratima.movietube.view.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private OnItemClickListener listener;
    private List<DataModel> mMovieList = new ArrayList<>();
    @NotNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_movie,
                parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        DataModel currentData = getItem(position);
        Context context = holder.moviePoster.getContext();
        String imgPosterUrl = ApiConstants.MOVIE_IMG_BASE_URL + currentData.getPoster_path();
        Log.d("imgPosterUrl","imgPosterUrl: " + imgPosterUrl);

        Glide.with(context).load(imgPosterUrl)
                .placeholder(R.drawable.ic_app_tube)
                .error(R.drawable.ic_app_tube)
                .into(holder.moviePoster);
    }

    private DataModel getItem(int position) {
        return mMovieList.get(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void submitList(@NotNull List<DataModel> movieList) {
        mMovieList.clear();
        mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_thumbnail);

            //click listener for Movie item
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = MovieViewHolder.this.getAdapterPosition();
                   if (listener != null && position != RecyclerView.NO_POSITION) {
                       listener.onItemClick(getItem(position));
                   }
               }
           });
        }
    }


}
