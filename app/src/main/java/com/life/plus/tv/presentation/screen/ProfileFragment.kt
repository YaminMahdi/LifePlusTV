package com.life.plus.tv.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.life.plus.tv.databinding.FragmentProfileBinding
import com.life.plus.tv.presentation.MainViewModel
import com.life.plus.tv.utils.collectWithLifecycle
import com.life.plus.tv.utils.setBounceClickListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserver()
        setupListener()
    }


    private fun setupObserver() {
        binding.apply {
            viewModel.currentUserState.collectWithLifecycle {
                if(it == null) return@collectWithLifecycle
                name.text = it.name
                phone.text = it.phone
                userName.text = it.userName
            }
        }
    }

    private fun setupListener() {
        binding.apply {
            btnBack.setBounceClickListener {
                findNavController().navigateUp()
            }
            btnLogout.setBounceClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }.setPositiveButton("Logout") { dialog, _ ->
                        dialog.dismiss()
                        viewModel.logout()
                    }.show()
            }
        }
    }
}