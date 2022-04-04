package com.mttnow.android.app_tmdb.ui.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.databinding.FragmentSearchBinding
import com.mttnow.android.app_tmdb.ui.adapter.MoviePagedListAdapter


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var viewModel: SearchViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel()

        binding.editTextSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val searchMovieText = binding.editTextSearch.text.toString()
                    viewModel.Search(searchMovieText)
                    noNullMovie()
                    true
                }
                else -> false
            }
        }
        // если у нас остася список фильмов то при переходе с фрагмента Details мы его показываем
        if (viewModel.moviePagedList != null) noNullMovie()
    }

    private fun noNullMovie() {
        val movieAdapter = MoviePagedListAdapter {
            //добавляем параметр для передачи его для MovieDetails
            val argTo = Bundle().apply {
                putInt("Movie_id", it)
            }
            findNavController().navigate(R.id.navigation_movie_detail, args = argTo)
        }

        val gridLayoutManager = GridLayoutManager(
            requireContext(),
            Const.SPAN_COUNT
        )
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1    // Movie_VIEW_TYPE will occupy 1 out of 2 span
                else return Const.SPAN_COUNT     //это исключение говорит о том сколько будет занимать NETWORK_VIEW_TYPE
            }
        }
        //adapter
        binding.rvMovieList.layoutManager = gridLayoutManager
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.adapter = movieAdapter

        viewModel.moviePagedList?.observe(viewLifecycleOwner, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.getNetworkState().observe(viewLifecycleOwner, Observer {
            binding.progressBarPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING
                ) View.VISIBLE else View.GONE
            binding.txtErrorPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR
                ) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun adapterSearch(){

    }

    private fun getViewModel(): SearchViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel() as T
            }
        })[SearchViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}