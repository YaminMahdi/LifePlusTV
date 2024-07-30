package com.life.plus.tv.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.life.plus.tv.R
import com.life.plus.tv.databinding.FragmentLoginBinding
import com.life.plus.tv.domain.ErrorType
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.presentation.MainViewModel
import com.life.plus.tv.utils.collectWithLifecycle
import com.life.plus.tv.utils.navigateSafe
import com.life.plus.tv.utils.setBounceClickListener


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.clipChildren = false
        container?.clipToPadding = false
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
            if(it is RequestState.Error) {
                when(it.type){
                    ErrorType.InvalidUserName -> binding.layoutUserName.error = it.error
                    ErrorType.InvalidPass -> binding.layoutPass.error = it.error
                    else -> Unit
                }
//                it.error.showToast()
            }
        }
    }

    private fun setupListener() {
        binding.apply {
            listOf(layoutUserName, layoutPass).forEach {txtLayout->
                txtLayout.editText?.doAfterTextChanged {
                    if(txtLayout.error != null)
                        txtLayout.error = null
                }
            }
            btnLogin.setBounceClickListener {
                viewModel.login(binding.userName.text.toString(), binding.pass.text.toString())
            }
            btnSignUp.setBounceClickListener {
                findNavController().navigateSafe(R.id.action_navLogin_to_navRegistration)
            }
        }
    }
}