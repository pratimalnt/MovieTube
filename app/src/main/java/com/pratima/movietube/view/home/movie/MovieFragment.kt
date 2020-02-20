package com.pratima.movietube.view.home.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pratima.movietube.R
import com.pratima.movietube.model.DataModel
import com.pratima.movietube.view.home.MovieAdapter
import com.pratima.movietube.viewmodel.MovieViewModel

class MovieFragment : Fragment() {
    private val TAG = this::class.java.simpleName
    private lateinit var mMovieViewModel: MovieViewModel
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: MovieAdapter? = null
    private  var movieList: List<DataModel> ? = null



    companion object {
        /**
         * Returns a new instance of this fragment
         */
        @JvmStatic
        fun newInstance(): MovieFragment {
            return MovieFragment().apply {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie, container, false)
        initViews(rootView)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        getPopularMovies()
    }

    private fun initViewModel() {

        // View Model
        mMovieViewModel =
            ViewModelProviders.of(this).get<MovieViewModel>(MovieViewModel::class.java)
        mMovieViewModel =
            ViewModelProviders.of(this.activity!!).get<MovieViewModel>(MovieViewModel::class.java)
    }

    private fun initViews(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.recyclerView)

        mAdapter = MovieAdapter()

        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = GridLayoutManager(activity, 3)
        mRecyclerView?.adapter = mAdapter

        mAdapter!!.setOnItemClickListener {
            showUpdateLevelSheet(it as DataModel)
        }
    }

    private fun getPopularMovies() {
        mMovieViewModel.popularMovieLiveData
            ?.observe(this, Observer {

                if (it != null && it.results.isNotEmpty()) {
                    Log.i(TAG, "data size :" + it.results.size)
                    movieList = it.results
                    mAdapter?.submitList(it.results)
                } else {
                    Log.i(TAG, "response is null")

                }
            })

        mMovieViewModel.searchResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.isNullOrEmpty()) {
                Log.i(TAG, "data size :" +  it.size)
                mAdapter?.submitList( it)
            } else {
                Log.i(TAG, "response is null")

            }
        })
    }

    private fun showUpdateLevelSheet(movieInfo: DataModel) {
        val bottomSheetFragment = MovieDetailBottomSheet(movieInfo)
        bottomSheetFragment.show(activity!!.supportFragmentManager, bottomSheetFragment.tag)
    }
}