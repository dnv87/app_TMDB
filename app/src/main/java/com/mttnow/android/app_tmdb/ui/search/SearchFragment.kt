package com.mttnow.android.app_tmdb.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.databinding.FragmentSearchBinding
import com.mttnow.android.app_tmdb.ui.adapter.MoviePagedListAdapter



class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var viewModel: SearchViewModel
//    lateinit var movieRepository: SearchMoviePagedListRepository

    // Это свойство допустимо только между onCreateView и onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // посылаем запрос после нажатия DONE
        binding.editTextSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            when (actionId){
                EditorInfo.IME_ACTION_DONE -> {
                    val textbuf = binding.editTextSearch.text.toString()
                    getRequestMovie(textbuf)
                    Log.d("my" , "onViewCreated $textbuf");
                    true
                }
                else -> false
            }
        }
    }

    private fun getRequestMovie (getMovie:String){

        val apiService : TMDBInterface = TMDBConnect.getClient()

//        movieRepository = SearchMoviePagedListRepository(apiService, getMovie)

        viewModel = getViewModel(apiService, getMovie)

        val movieAdapter = MoviePagedListAdapter{
            //добавляем параметр для передачи его для MovieDetails
            val argTo = Bundle().apply {
                putInt("Movie_id", it)
            }
            findNavController().navigate(R.id.navigation_movie_detail, args = argTo )
        }


        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
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

    private fun getViewModel(apiService : TMDBInterface, getMovie:String): SearchViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(apiService, getMovie) as T
            }
        })[SearchViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}