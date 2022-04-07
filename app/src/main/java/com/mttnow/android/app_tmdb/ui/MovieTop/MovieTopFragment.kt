package com.mttnow.android.app_tmdb.ui.MovieTop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.databinding.FragmentMovieTopBinding
import com.mttnow.android.app_tmdb.ui.adapter.MoviePagedListAdapter


class MovieTopFragment : Fragment() {

    private var _binding: FragmentMovieTopBinding? = null
    private lateinit var viewModel: MovieTopViewModel
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieTopBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MoviePagedListAdapter {
            val action =
                MovieTopFragmentDirections.actionNavigationMovieTopToNavigationMovieDetail(it)
            findNavController().navigate(action)
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == Const.MOVIE_VIEW_TYPE) return 1    // Movie_VIEW_TYPE will occupy 1 out of 2 span
                else return 2                                              // NETWORK_VIEW_TYPE will occupy all 2 span
            }
        }

        //adapter
        binding.rvMovieList.layoutManager = gridLayoutManager
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.adapter = movieAdapter

        //инициализировали viewModel
        viewModel = getViewModel()

        with(viewModel) {
            //при создании фрагмента проверяем если ли загруженные данные
            if (pageIsEmpty()) {
                getLiveMoviePagedList().observe(viewLifecycleOwner, Observer {
                    movieAdapter.submitList(it)
                })
            } else {
                moviePagedList?.observe(viewLifecycleOwner, Observer {
                    movieAdapter.submitList(it)
                })
            }

            getNetworkState().observe(viewLifecycleOwner, Observer {
                binding.progressBarNextPage.visibility =
                    if (pageIsEmpty() && it == NetworkState.LOADING
                    ) View.VISIBLE else View.GONE
                binding.progressBarTop.visibility =
                    if (pageIsEmpty() || it == NetworkState.FIRSTLOADING
                    ) View.VISIBLE else View.GONE
                binding.txtErrorTop.visibility =
                    if (pageIsEmpty() && it == NetworkState.ERROR
                    ) View.VISIBLE else View.GONE

                if (!pageIsEmpty()) {
                    movieAdapter.setNetworkState(it)
                }
            })
        }

    }


    private fun getViewModel(): MovieTopViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieTopViewModel() as T
            }
        })[MovieTopViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}