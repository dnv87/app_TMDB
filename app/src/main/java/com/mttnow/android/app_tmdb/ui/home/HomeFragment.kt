package com.mttnow.android.app_tmdb.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var  thiscontext: Context

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    lateinit var movieRepository: MoviePagedListRepository

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val apiService : TMDBInterface = TMDBConnect.getClient()
        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()
        thiscontext = container!!.getContext();

        val movieAdapter = MoviePagedListAdapter(thiscontext)

        val gridLayoutManager = GridLayoutManager(thiscontext, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        }

        //adapter
        binding.rvMovieList.layoutManager = gridLayoutManager
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.adapter = movieAdapter

        viewModel.moviePagedList.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            binding.progressBarPopular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING
            ) View.VISIBLE else View.GONE
            binding.txtErrorPopular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR
            ) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

        return root
    }

    private fun getViewModel(): HomeViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(movieRepository) as T
            }
        })[HomeViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}