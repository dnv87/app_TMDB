package com.mttnow.android.app_tmdb.ui.Autorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val btnAutoriz = binding.autorizBtn

        // подгружаем сохранённые данные
        if (viewModel.isAutorize()){
            val getLogPass = viewModel.getLogPassSP()
//            binding.login.text = getLogPass.first
//            binding.password.text = getLogPass.second
        }


        btnAutoriz.setOnClickListener {
            val eitTextInputLogin = binding.login.text.toString()
            val eitTextInputPassword = binding.password.text.toString()
            if (viewModel.checkBtnAutoriz(eitTextInputLogin, eitTextInputPassword)){
                //авторизовались
                binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_check_circle_24)
                //через Rх-Java послать разрешение на загрузку данных в 1е окно (сделать видимым recyclerview)
            }else{
                binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_cancel_24)
                //если есть разрешение загрузки данных в 1м окне, то отправить команду (скрыть recyclerview)
            }

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}