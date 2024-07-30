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
import com.life.plus.tv.databinding.FragmentHomeBinding
import com.life.plus.tv.presentation.MainViewModel
import com.life.plus.tv.presentation.ShowAdapter
import com.life.plus.tv.utils.closeKeyboard
import com.life.plus.tv.utils.collectWithLifecycle
import com.life.plus.tv.utils.gone
import com.life.plus.tv.utils.invisible
import com.life.plus.tv.utils.navigateSafe
import com.life.plus.tv.utils.setBounceClickListener
import com.life.plus.tv.utils.setStatusAppearance
import com.life.plus.tv.utils.showToast
import com.life.plus.tv.utils.visible


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private val showAdapter by lazy { ShowAdapter(onItemClick = {
        findNavController().navigateSafe(R.id.action_navHome_to_navShowDetails)
    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.keepSplashScreen = false
        container?.clipChildren = false
        container?.clipToPadding = false
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupObserver()
        setupListener()
    }

    private fun setupUI() {
        binding.root.clipChildren = false
        binding.root.clipToPadding = false
        binding.searchRecyclerView.adapter = showAdapter
        activity.setStatusAppearance(isLight = true)
    }

    private fun setupObserver() {
        viewModel.currentUserState.collectWithLifecycle {
            if (it != null) {
                binding.name.text = it.name.split(" ").last()
            }
        }
        viewModel.searchList.collectWithLifecycle {
            it?.let {
                showAdapter.submitList(it)
                if (it.isEmpty()) {
                    binding.noHistory.text = getString(R.string.no_shows_found)
                    binding.noHistory.visible()
                } else
                    binding.noHistory.gone()
            }
            binding.progressBar.invisible()
            activity?.closeKeyboard()
        }
        binding.searchBar.doAfterTextChanged {
            if(it.toString().length > 2){
                binding.progressBar.visible()
                viewModel.searchShow(it.toString()) { error ->
                    error.showToast()
                    if (showAdapter.currentList.isEmpty()) {
                        binding.noHistory.text = getString(R.string.no_shows_found)
                        binding.noHistory.visible()
                    }
                }
            }else if (showAdapter.currentList.isEmpty()) {
                binding.noHistory.text = getString(R.string.enter_search_keyword)
                binding.noHistory.visible()
            }
        }
    }

    private fun setupListener() {
        binding.apply {
            btnProfile.setBounceClickListener {
                viewModel.logout()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        activity.setStatusAppearance(isLight = false)
    }

}