package com.mttnow.android.app_tmdb.ui.details

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mttnow.android.app_tmdb.MainActivity
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.MAIN
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.databinding.FragmentMovieDetailsBinding
import com.mttnow.android.app_tmdb.modeldata.MovieDetails
import java.text.NumberFormat
import java.util.*


class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var  thiscontext: Context
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    val args:MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        thiscontext = container!!.getContext();
        return binding.root
    }

//    private fun setupTopBar() {
//        val navController = findNavController()
//        toolbar.setupWithNavController(navController)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val a = requireContext()
        val b = context

        (requireActivity() as? MainActivity)?.test()

        val argmovieId: Int = args.movieId
        val nav = MAIN.findViewById<BottomNavigationView>(R.id.nav_view)
        nav.visibility = View.GONE


        val movieId:Int = argmovieId


        val apiService : TMDBInterface = TMDBConnect.getClient()
        movieRepository = MovieDetailsRepository(apiService)


        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI( it: MovieDetails){
        binding.movieTitle.text = it.title
        binding.movieTagline.text = it.tagline
        binding.movieReleaseDate.text = it.releaseDate
        binding.movieRating.text = it.rating.toString()
        binding.movieRuntime.text = it.runtime.toString() + " minutes"
        binding.movieOverview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        binding.movieBudget.text = formatCurrency.format(it.budget)
        binding.movieRevenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = Const.THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE500 + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivMoviePoster);
    }


    private fun getViewModel(movieId:Int): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieRepository,movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val nav = MAIN.findViewById<BottomNavigationView>(R.id.nav_view)
        nav.visibility = View.VISIBLE
        _binding = null
    }
}