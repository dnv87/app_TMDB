package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.databinding.FragmentMoviePopularBinding


class MoviePopularFragment : Fragment() {

    private var _binding: FragmentMoviePopularBinding? = null

    //    private lateinit var viewModel: MoviePopularViewModel
    private val binding get() = _binding!!

    //инициализировали viewModel
    private val viewModel = MoviePopularViewModelNoPaging()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviePopularBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MoviePopularAdapter {
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


        //загрузка первой страницы

        //каждый раз при загрузки фрагмента начинаем с первой страницы
        viewModel.itemsMovie.observe(viewLifecycleOwner, Observer {
            Log.d("my", "observe")
            movieAdapter.addNewListToList(it)
        })

//        viewModel.itemsMovie.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                NetworkState.LOADING -> {
//                    movieAdapter.addLoadingToList()
//                }
////                NetworkState.LOADED -> {
////                    movieAdapter.deleteLoadingToList()
////                }
//            }
//        })
//        viewModel.loadNextPage(3)
    }


    private fun inintListebers() {
        binding.rvMovieList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        //function that add new elements to my recycler view
                    }
                }
            })
    }

//    private fun getViewModel(): MoviePopularViewModel {
//        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return MoviePopularViewModel() as T
//            }
//        })[MoviePopularViewModel::class.java]
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}