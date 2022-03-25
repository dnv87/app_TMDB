package com.mttnow.android.app_tmdb.ui.Movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mttnow.android.app_tmdb.data.MAIN
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.databinding.FragmentMovieBinding
import com.mttnow.android.app_tmdb.modeldata.Movie


class MovieFragment : Fragment(){

    lateinit var  thiscontext: Context

    private var _binding: FragmentMovieBinding? = null
    private lateinit var viewModel: MovieViewModel
    lateinit var movieRepository: MoviePagedListRepository

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: MovieFragmentArgs by navArgs() // получаем аргумент Safe Args


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        thiscontext = container!!.getContext();
        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getMovie = args.top
//        Log.d("my", "!!!!!!${getMovie.toString()}")

        val apiService : TMDBInterface = TMDBConnect.getClient()
        movieRepository = MoviePagedListRepository(apiService, getMovie)

        viewModel = getViewModel()

        val movieAdapter = MoviePagedListAdapter{

            val action = MovieFragmentDirections.actionNavigationMovieToNavigationMovieDetail(it)
            findNavController().navigate(action)
        }


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
    }

    private fun getViewModel(): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieRepository) as T
            }
        })[MovieViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}