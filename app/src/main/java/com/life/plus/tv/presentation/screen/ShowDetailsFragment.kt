package com.life.plus.tv.presentation.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.life.plus.tv.databinding.FragmentShowDetailsBinding
import com.life.plus.tv.presentation.MainViewModel
import com.life.plus.tv.presentation.adapter.GenreAdapter
import com.life.plus.tv.utils.gone
import com.life.plus.tv.utils.loadDrawable
import com.life.plus.tv.utils.setBounceClickListener
import com.life.plus.tv.utils.showToast


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
        setupListener()
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        binding.apply {
            viewModel.currentItem?.let {
                image.loadDrawable(it.mediumImage)
                name.text = it.name
                time.text = it.premiered
                rate.text = it.rating.toString()
                watchTime.text = ": ${it.averageRuntime} Min"
                country.text = it.country
                language.text = it.language
                score.text = "%.2f".format(it.score)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    summary.text = Html.fromHtml(it.summary, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    @Suppress("DEPRECATION")
                    summary.text = Html.fromHtml(it.summary)
                }
                if(it.genres.isNotEmpty())
                    genreRecyclerView.adapter = GenreAdapter(it.genres)
                else
                    genreRecyclerView.gone()
            } ?: "Show Not Found".showToast()
        }
    }

    private fun setupListener() {
        val intent = CustomTabsIntent.Builder()
            .build()
        val show = viewModel.currentItem
        binding.apply {
            btnBack.setBounceClickListener {
                findNavController().navigateUp()
            }
            btnImdb.setBounceClickListener {
                if(show?.imdb.isNullOrEmpty()) {
                    "Link Not Found".showToast()
                    return@setBounceClickListener
                }
                intent.launchUrl(requireContext(), Uri.parse(show?.imdb))
            }
            btnTvMaze.setBounceClickListener {
                if(show?.url.isNullOrEmpty()) {
                    "Link Not Found".showToast()
                    return@setBounceClickListener
                }
                intent.launchUrl(requireContext(), Uri.parse(show?.url))
            }
            btnWeb.setBounceClickListener {
                if(show?.officialSite.isNullOrEmpty()) {
                    "Link Not Found".showToast()
                    return@setBounceClickListener
                }
                intent.launchUrl(requireContext(), Uri.parse(show?.officialSite))
            }
        }

    }
}