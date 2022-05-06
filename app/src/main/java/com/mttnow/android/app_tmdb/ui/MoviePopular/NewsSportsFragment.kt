package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.databinding.FragmentNewsSportsBinding
import com.mttnow.android.app_tmdb.ui.growapp_Package.InfiniteScrollListener
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class NewsSportsFragment : Fragment() {

    private var _binding: FragmentNewsSportsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsSportsViewModelNoPaging

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Fragment", "onCreateView")
        _binding = FragmentNewsSportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Fragment", "onViewCreated")
        //инициализировали viewModel
        viewModel = ViewModelProvider(this)[NewsSportsViewModelNoPaging::class.java]

        // проверям авторизацию пользователя
        viewModel.validate.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.rvNewsList.visibility = View.VISIBLE
                binding.txtErrorPopular.visibility = View.GONE
            } else {
                binding.txtErrorPopular.visibility = View.VISIBLE
                binding.txtErrorPopular.text = "Авторизуйся!!!"
                binding.rvNewsList.visibility = View.GONE
            }
        })
        viewModel.checkValidation()

        if (viewModel.validate.value == null){
            binding.txtErrorPopular.visibility = View.VISIBLE
            binding.txtErrorPopular.text = "идёт авторизация"
            binding.rvNewsList.visibility = View.GONE
        }


        //инициализируем
        val (newsAdapter, recyclerView, scrollListener) = setupAdapter()
        recyclerView.addOnScrollListener(scrollListener)

        //слушаем есть ли новые странички
        viewModel.itemsNews.observe(viewLifecycleOwner, Observer {
            newsAdapter.addNewListToList(it)
        })

        //загрузка первой страницы
        viewModel.loadItems()

        //отслеживаем состояния загрузки новых страничек
        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.FIRSTLOADING -> {
                    binding.progressBarStart.visibility = View.VISIBLE
                }
                NetworkState.LOADING -> {
                    scrollListener.showProgressIndicator()
                }
                NetworkState.LOADED -> {
                    binding.progressBarStart.visibility = View.GONE
                    scrollListener.hideProgressIndicator()
                }
            }
        }
        setButtonAction(newsAdapter)
    }

    override fun onDestroyView() {
        Log.d("Fragment", "onDestroyView")
        super.onDestroyView()
        _binding = null
    }


    /**
     * инициализируем Adapter, RecyclerView, ScrollListener
     */
    private fun setupAdapter(): Triple<NewsSportAdapter, RecyclerView, InfiniteScrollListener> {
        val newsAdapter = NewsSportAdapter {
            val argTo = Bundle().apply {
                putInt("position", it)
            }
//            findNavController().navigate(R.id.navigation_movie_detail, args = argTo)
        }

        val recyclerView = binding.rvNewsList
        recyclerView.recycledViewPool.setMaxRecycledViews(
            Const.MOVIE_VIEW_TYPE,
            NewsSportAdapter.MAX_POOL_SIZE
        )

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = newsAdapter.getItemViewType(position)
                return if (viewType == Const.MOVIE_VIEW_TYPE) 1 else 2
            }
        }

        //adapter
        binding.rvNewsList.layoutManager = gridLayoutManager
        binding.rvNewsList.setHasFixedSize(true)
        binding.rvNewsList.adapter = newsAdapter

        //scrollListener
        val scrollListener = InfiniteScrollListener(
            recyclerView,
            newsAdapter,
            callback = { offset: Int ->
//                Log.d("scrollListener", offset.toString())
                viewModel.loadItems()
            }
        )
        return Triple(newsAdapter, recyclerView, scrollListener)
    }

    /**
     * всё понятно из названия
     */
    private fun setButtonAction(newsAdapter: NewsSportAdapter) {

        val btnChangeColorToId = binding.setGreenColorIdBtn
        btnChangeColorToId.setOnClickListener {
            val eitTextInputId = binding.editTextInputId.text.toString()
            newsAdapter.updateColorTitle(eitTextInputId.toInt())
        }

        var disposable: Disposable? = null
        val startTimer = binding.startBtn

        startTimer.setOnClickListener {
            if ((disposable == null) || disposable?.isDisposed!!) {
                newsAdapter.defaultListColorTitle()
                disposable = Observable.timer(1000, TimeUnit.MILLISECONDS)
                    .repeat() //to perform your task every 1 seconds
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        newsAdapter.updateListTimer()
                    }
            }
        }

        val stopTimer = binding.stopBtn
        stopTimer.setOnClickListener {
            disposable?.dispose()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Fragment", "onCreate")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("Fragment", "onAttach")
    }

    override fun onStart() {
        super.onStart()

        Log.d("Fragment", "onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d("Fragment", "onResume")
    }

    override fun onStop() {
        Log.d("Fragment", "onStop")

        super.onStop()
    }

    override fun onDestroy() {
        Log.d("Fragment", "onDestroy")

        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("Fragment", "onDetach")

        super.onDetach()
    }


    override fun onPause() {
        Log.d("Fragment", "onPause")
        super.onPause()
    }
}