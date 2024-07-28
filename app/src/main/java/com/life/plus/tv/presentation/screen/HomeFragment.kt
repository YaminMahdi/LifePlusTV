package com.life.plus.tv.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.life.plus.tv.R
import com.life.plus.tv.databinding.FragmentHomeBinding
import com.life.plus.tv.databinding.FragmentLoginBinding
import com.life.plus.tv.presentation.MainViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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

    }

    private fun setupListener() {

    }

}