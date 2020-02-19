package com.pratima.movietube.view.home.tvshows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pratima.movietube.R
import com.pratima.movietube.model.TvShows
import com.pratima.movietube.viewmodel.TvShowsViewModel
import androidx.lifecycle.Observer
import com.pratima.movietube.viewmodel.SearchViewModel

class TvShowsFragment : Fragment() {
    private val TAG = this::class.java.simpleName
    private lateinit var mSearchViewModel: SearchViewModel
    private lateinit var mTvShowsViewModel: TvShowsViewModel
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: TvShowsAdapter? = null

    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): TvShowsFragment {
            return TvShowsFragment().apply {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie, container, false)

        initViewModel()
        initViews(rootView)
        getPopularTvShows()

        return rootView
    }

    private fun initViewModel() {

        // View Model
        mTvShowsViewModel =
            ViewModelProviders.of(this).get<TvShowsViewModel>(TvShowsViewModel::class.java)
    }

    private fun initViews(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.recyclerView)

        mAdapter = TvShowsAdapter()

        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = GridLayoutManager(activity, 3)
        mRecyclerView?.adapter = mAdapter

        mAdapter!!.setOnItemClickListener {
            showUpdateLevelSheet(it as TvShows)
        }
    }

    private fun getPopularTvShows() {
        mTvShowsViewModel.popularTvShowsData
            .observe(this, Observer {

                if (it != null && it.tvShowsList.isNotEmpty()) {
                    Log.i(TAG, "data size :" + it.tvShowsList.size)
                    mAdapter!!.submitList(it.tvShowsList)
                } else {
                    Log.i(TAG, "response is null")

                }
            })
    }

    private fun showUpdateLevelSheet(tvShowsInfo: TvShows) {
        val bottomSheetFragment = TvShowsDetailsBottomSheet(tvShowsInfo)
        bottomSheetFragment.show(activity!!.supportFragmentManager, bottomSheetFragment.tag)
    }

}