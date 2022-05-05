package com.mttnow.android.app_tmdb.ui.Autorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.databinding.FragmentMovieTopBinding


class AutorizFragment : Fragment() {

    private var _binding: FragmentMovieTopBinding? = null
    private lateinit var viewModel: AutorizViewModel
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
        //инициализировали viewModel
        viewModel = AutorizViewModel()

        val btnValidate = binding.autorizBtn

        //заполняем данными если они есть
        binding.login.setText(viewModel.user.first)
        binding.password.setText(viewModel.user.second)

        viewModel.validate.observe(viewLifecycleOwner, Observer{
            choisImage(it)
        })
        viewModel.checkValidation()

        //обрабатываем нажатие кнопки
        btnValidate.setOnClickListener {
            val eitTextUser = Pair(binding.login.text.toString(), binding.password.text.toString())
            viewModel.updateUser(eitTextUser)
            viewModel.checkValidation()
        }
    }

    private fun choisImage(check: Boolean) {
        if (check) {
            binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_check_circle_24)
        } else {
            binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_cancel_24)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}