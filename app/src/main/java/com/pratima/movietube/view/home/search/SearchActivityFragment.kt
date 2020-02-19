package com.pratima.movietube.view.home.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pratima.movietube.R
import com.pratima.movietube.model.Search
import com.pratima.movietube.view.home.movie.MovieAdapter
import com.pratima.movietube.view.lttsPlayer.PlayerActivity
import com.pratima.movietube.viewmodel.SearchViewModel


/**
 * A placeholder fragment containing a simple view.
 */
class SearchActivityFragment : Fragment(), TextView.OnEditorActionListener {


    private val TAG = this::class.java.simpleName
    private lateinit var mSearchViewModel: SearchViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mSearchView: EditText
    private lateinit var mAdapter: MovieAdapter
    private lateinit var mSearchList: List<Search>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        initViews(rootView)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {

        mSearchViewModel =
            ViewModelProviders.of(this.activity!!).get<SearchViewModel>(SearchViewModel::class.java)
    }

    private fun initViews(rootView: View) {
        mProgressBar = rootView.findViewById(R.id.search_progress)
        mSearchView = rootView.findViewById(R.id.search_bar)
        mRecyclerView = rootView.findViewById(R.id.recyclerView)

        mAdapter = MovieAdapter()
        mAdapter.setOnItemClickListener {
            playVideo()
        }

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(activity, 3)
        mRecyclerView.adapter = mAdapter

        mSearchView.setOnEditorActionListener(this)

    }

    override fun onEditorAction(view: TextView, actionId: Int, keyEvent: KeyEvent?): Boolean {
        var handled = false
        if (actionId == EditorInfo.IME_ACTION_DONE
            || keyEvent!!.action == KeyEvent.ACTION_DOWN
            || keyEvent!!.action == KeyEvent.KEYCODE_ENTER
        ) {

            //your code here
            handled = true
            performSearch()
            mSearchView.clearFocus()
            val inputMethodManager: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(mSearchView.windowToken, 0)
        }
        return handled
    }

    private fun performSearch() {
        val searchText = mSearchView.text
        Log.i(TAG, "Search test=" + searchText)

        mSearchViewModel.setSearchQuery(searchText.toString())

        mSearchViewModel.searchResponse.observe(viewLifecycleOwner, Observer {
            if (it != null && !it.isNullOrEmpty()) {
                Log.i(TAG, "data size :" + it.size)
                mAdapter.submitList(it)
            } else {
                Log.i(TAG, "response is null")

            }
        })
    }

    private fun playVideo() {
        val playIntent = Intent(context, PlayerActivity::class.java)
        startActivity(playIntent)

    }
}
