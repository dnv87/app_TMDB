package com.mttnow.android.app_tmdb.ui.Movie

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.databinding.FragmentMovieBinding
import com.mttnow.android.app_tmdb.ui.adapter.MoviePagedListAdapter


class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private lateinit var viewModel: MovieViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args: MovieFragmentArgs by navArgs() // получаем аргумент Safe Args

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getQueryMovie = args.top

        //инициализировали viewModel
        viewModel = getViewModel()
        //передали параметр загрузки Movie во viewModel
        viewModel.getMovie(getQueryMovie)


        val movieAdapter = MoviePagedListAdapter {
            val argTo = Bundle().apply {
                putInt("Movie_id", it)
            }
            findNavController().navigate(R.id.navigation_movie_detail, args = argTo)
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

        //посылаем запрос Movie во viewModel и слушаем ответ
        viewModel.getLiveMoviePagedList().observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
            binding.progressBarPopular.visibility =
                if (/*viewModel.listIsEmpty() &&*/ it == NetworkState.LOADING
                ) View.VISIBLE else View.GONE
            binding.txtErrorPopular.visibility =
                if (/*viewModel.listIsEmpty() &&*/ it == NetworkState.ERROR
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
                return MovieViewModel() as T
            }
        })[MovieViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}