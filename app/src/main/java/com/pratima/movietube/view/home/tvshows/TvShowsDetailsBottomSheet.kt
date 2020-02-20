package com.pratima.movietube.view.home.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pratima.movietube.R
import com.pratima.movietube.api.ApiConstants
import com.pratima.movietube.model.DataModel
import com.pratima.movietube.view.lttsPlayer.PlayerActivity

class TvShowsDetailsBottomSheet(tvShowsInfo: DataModel) : BottomSheetDialogFragment()  {
    private val mTvShowsInfo = tvShowsInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.sheet_update_level, container, false)
        dialog!!.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).state =
                BottomSheetBehavior.STATE_EXPANDED
        }
        initViews(rootView)
        return rootView
    }


    private fun initViews(rootView: View?) {
        val movieTitle: TextView = rootView!!.findViewById(R.id.sheet_title)
        val posterBackDrop: ImageView = rootView!!.findViewById(R.id.iv_backdrop)
        val movieRating: TextView = rootView.findViewById(R.id.tv_rating)
        val watchNow: TextView = rootView.findViewById(R.id.btn_watch_now)
        val movieDescription: TextView = rootView.findViewById(R.id.tv_description)
        val closeSheet: ImageButton = rootView.findViewById(R.id.btn_close_sheet)

        //Close button click
        closeSheet.setOnClickListener {
            dismiss()
        }

        //Watch Now button click
        watchNow.setOnClickListener {
            playVideo()
        }

        movieTitle.text = mTvShowsInfo.original_name
        movieDescription.text = mTvShowsInfo.overview
        movieRating.text = mTvShowsInfo.vote_average.toString()

        val imgPosterUrl = ApiConstants.MOVIE_IMG_BASE_URL + mTvShowsInfo.backdrop_path

        Glide.with(context!!).load(imgPosterUrl)
            .placeholder(R.drawable.ic_app_tube)
            .error(R.drawable.ic_app_tube)
            .into(posterBackDrop)
    }

    private fun playVideo() {
        val playIntent = Intent(context, PlayerActivity::class.java)
        startActivity(playIntent)

    }
}