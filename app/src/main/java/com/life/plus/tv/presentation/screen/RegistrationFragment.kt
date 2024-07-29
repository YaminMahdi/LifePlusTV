package com.life.plus.tv.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.life.plus.tv.databinding.FragmentRegistrationBinding
import com.life.plus.tv.domain.ErrorType
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.UserInfo
import com.life.plus.tv.presentation.MainViewModel
import com.life.plus.tv.utils.collectWithLifecycle
import com.life.plus.tv.utils.isValidUsername
import com.life.plus.tv.utils.setBounceClickListener
import com.life.plus.tv.utils.showToast
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupObserver()
        setupListener()
    }

    private fun setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            binding.root.clipChildren = false
            binding.root.clipToPadding = false
            insets
        }
    }

    private fun setupObserver() {
        viewModel.regInfo.collectWithLifecycle {
            when(it){
                is RequestState.Success -> {
                    findNavController().navigateUp()
                    it.data.showToast()
                }
                is RequestState.Error -> {
                    when(it.type){
                        ErrorType.None -> Unit
                        ErrorType.InvalidUserName -> binding.layoutUserName.error = it.error
                        ErrorType.InvalidName -> binding.layoutName.error = it.error
                        ErrorType.InvalidPhone -> binding.layoutPhone.error = it.error
                        ErrorType.InvalidPass -> binding.layoutPass.error = it.error
                        ErrorType.InvalidRePass -> binding.layoutRePass.error = it.error
                    }
                }
                else -> Unit
            }
        }
    }

    private fun setupListener() {
        binding.apply {
            listOf(layoutName, layoutUserName, layoutPhone, layoutPass, layoutRePass).forEach {txtLayout->
                txtLayout.editText?.doAfterTextChanged {
                    if(txtLayout.error != null)
                        txtLayout.error = null
                }
            }
            var userNameJob : Job? = null
            userName.doAfterTextChanged {
                userNameJob?.cancel()
                if(!it.toString().isValidUsername()){
                    layoutUserName.error = "Invalid User Name"
                    return@doAfterTextChanged
                }
                if(it.toString().isNotBlank()){
                    userNameJob = lifecycleScope.launch {
                        delay(500)
                        val isExist = viewModel.isUserNameExist(it.toString())
                        if(isExist)
                            layoutUserName.error = "User Name taken"
                        else
                            layoutUserName.helperText = "Available"
                    }
                }else layoutUserName.helperText = null
            }
            btnBack.setBounceClickListener {
                findNavController().navigateUp()
            }
            btnSignUp.setBounceClickListener {
                viewModel.register(
                    UserInfo(
                        userName = userName.text.toString(),
                        name = name.text.toString(),
                        phone = phone.text.toString(),
                        password = pass.text.toString()
                    ), rePass = rePass.text.toString()
                )
            }
        }

    }
}