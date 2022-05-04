package com.mttnow.android.app_tmdb.ui.Autorization

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.mttnow.android.app_tmdb.MainActivity
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.databinding.FragmentMovieTopBinding


class AutorizFragment : Fragment() {

    private var _binding: FragmentMovieTopBinding? = null
    private lateinit var viewModel: AutorizViewModel
    private val binding get() = _binding!!
    private val MAIN = (requireActivity() as MainActivity)


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
        val log = MAIN.SharedPrefGet(Const.LOGIN)
        binding.login.setText(log)
        val pass = MAIN.SharedPrefGet(Const.PASSWORD)
        binding.password.setText(pass)
        val imageTrue = binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_check_circle_24)
        val imageFalse = binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_cancel_24)

        if (viewModel.checkBtnAutoriz(log, pass)) {
            imageTrue
        } else {
            imageFalse
        }

        btnAutoriz.setOnClickListener {
            val eitTextInputLogin = binding.login.text.toString()
            val eitTextInputPassword = binding.password.text.toString()
            if (viewModel.checkBtnAutoriz(eitTextInputLogin, eitTextInputPassword)) {
                //авторизовались
                imageTrue

                MAIN.SharedPrefPut(Const.LOGIN, eitTextInputLogin)
                MAIN.SharedPrefPut(Const.PASSWORD, eitTextInputPassword)

                //через Rх-Java послать разрешение на загрузку данных в 1е окно (сделать видимым recyclerview)
            } else {
                imageFalse
                //если есть разрешение загрузки данных в 1м окне, то отправить команду (скрыть recyclerview)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}