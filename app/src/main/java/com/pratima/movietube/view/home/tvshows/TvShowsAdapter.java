package com.pratima.movietube.view.home.tvshows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pratima.movietube.R;
import com.pratima.movietube.api.ApiConstants;
import com.pratima.movietube.model.Movie;
import com.pratima.movietube.model.TvShows;
import com.pratima.movietube.view.OnItemClickListener;
import com.pratima.movietube.view.home.movie.MovieAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder> {

    private OnItemClickListener listener;
    private List<TvShows> mTvShowsList = new ArrayList<>();

    @NotNull
    @Override
    public TvShowsAdapter.TvShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_movie,
                parent, false);
        return new TvShowsAdapter.TvShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsAdapter.TvShowsViewHolder holder, int position) {

        TvShows currentData = getItem(position);
        Context context = holder.moviePoster.getContext();
        String imgPosterUrl = ApiConstants.MOVIE_IMG_BASE_URL + currentData.getPosterPath();

        Glide.with(context).load(imgPosterUrl)
                .placeholder(R.drawable.ic_app_tube)
                .error(R.drawable.ic_app_tube)
                .into(holder.moviePoster);
    }

    private TvShows getItem(int position) {
        return mTvShowsList.get(position);
    }

    @Override
    public int getItemCount() {
        return mTvShowsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void submitList(@NotNull List<TvShows> tvShowsList) {
        mTvShowsList.clear();
        mTvShowsList.addAll(tvShowsList);
        notifyDataSetChanged();
    }


    class TvShowsViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;

        TvShowsViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = TvShowsAdapter.TvShowsViewHolder.this.getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }


}
