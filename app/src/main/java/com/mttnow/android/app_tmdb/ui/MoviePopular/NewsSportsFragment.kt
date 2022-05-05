package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.databinding.FragmentNewsSportsBinding
import com.mttnow.android.app_tmdb.ui.MoviePopular.NewsSportAdapter.Companion.MAX_POOL_SIZE
import com.mttnow.android.app_tmdb.ui.growapp_Package.InfiniteScrollListener
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class NewsSportsFragment : Fragment() {

    private var _binding: FragmentNewsSportsBinding? = null
    private val binding get() = _binding!!

    //инициализировали viewModel
    private val viewModel = NewsSportsViewModelNoPaging()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsSportsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // проверям авторизацию пользователя
        viewModel.checkValidation()
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


        //инициализируем
        val (newsAdapter, recyclerView, scrollListener) = setupAdapter()
        recyclerView.addOnScrollListener(scrollListener)

        //загрузка первой страницы
        viewModel.loadItems()

        //слушаем есть ли новые странички
        viewModel.itemsNews.observe(viewLifecycleOwner, Observer {
            newsAdapter.addNewListToList(it)
        })

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
        recyclerView.recycledViewPool.setMaxRecycledViews(Const.MOVIE_VIEW_TYPE, MAX_POOL_SIZE)

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
//            Log.d("my", eitTextInputId)
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
//                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d("ComingHere", "Inside_Timer")
                        newsAdapter.updateListTimer()
                    }
            }
        }

        val stopTimer = binding.stopBtn
        stopTimer.setOnClickListener {
            disposable?.dispose()
        }
    }
}