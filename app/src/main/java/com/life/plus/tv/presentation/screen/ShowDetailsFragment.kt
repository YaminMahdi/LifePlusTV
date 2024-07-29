package com.life.plus.tv.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.life.plus.tv.databinding.FragmentShowDetailsBinding
import com.life.plus.tv.presentation.MainViewModel

class ShowDetailsFragment : Fragment() {
    private lateinit var binding: FragmentShowDetailsBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
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