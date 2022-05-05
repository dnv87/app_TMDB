package com.mttnow.android.app_tmdb.ui.Autorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.databinding.FragmentMovieTopBinding
import com.mttnow.android.app_tmdb.ui.utils.ModelPreferencesManager


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

        val user = ModelPreferencesManager.SharedPrefGet(Const.USER)
        binding.login.setText(user.first)
        binding.password.setText(user.second)

        val initValid = viewModel.checkValidation(user)
        changeData(initValid)




        btnAutoriz.setOnClickListener {
            val eitTextUser = Pair(binding.login.text.toString(), binding.password.text.toString())
            val clickValid = viewModel.checkValidation(eitTextUser)

            changeData(clickValid)
            saveOrCleanSharedPref(clickValid, eitTextUser)
        }
    }

    private fun changeData(check: Boolean) {
        if (check) {
            binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_check_circle_24)
        } else {
            binding.ivAutoriz.setImageResource(R.drawable.ic_baseline_cancel_24)
        }
    }

    private fun saveOrCleanSharedPref(check: Boolean, user: Pair<String, String>) {
        if (check) {
            ModelPreferencesManager.SharedPrefPut(Const.USER, user)
        } else {
            ModelPreferencesManager.SharedPrefClean()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}