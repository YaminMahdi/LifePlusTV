package com.life.plus.tv.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.life.plus.tv.databinding.FragmentLoginBinding
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.presentation.MainViewModel
import com.life.plus.tv.utils.collectWithLifecycle
import com.life.plus.tv.utils.setBounceClickListener
import com.life.plus.tv.utils.showToast


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupObserver()
        setupListener()
    }

    private fun setupUI() {

    }

    private fun setupObserver() {
        viewModel.loginInfo.collectWithLifecycle {
            if(it is RequestState.Error)
                it.error.showToast()

        }
    }

    private fun setupListener() {
        binding.btnLogin.setBounceClickListener {
            viewModel.login(binding.userName.text.toString(), binding.pass.text.toString())
        }
    }
}