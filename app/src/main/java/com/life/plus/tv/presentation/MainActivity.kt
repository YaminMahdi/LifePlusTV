package com.life.plus.tv.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.life.plus.tv.R
import com.life.plus.tv.databinding.ActivityMainBinding
import com.life.plus.tv.utils.collectWithLifecycle
import com.life.plus.tv.utils.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { viewModel.keepSplashScreen }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupObserver()

    }

    private fun setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val isImeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val bottom = if (isImeVisible) ime.bottom else systemBars.bottom
            binding.root.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom)
            insets
        }
    }

    private fun setupObserver() {
        viewModel.currentUser
            .distinctUntilChanged()
            .collectWithLifecycle {
                if(it != null)
                    findNavController(R.id.main_nav_host).navigateSafe(R.id.action_navLogin_to_navHome)
                else {
                    viewModel.keepSplashScreen = false
                    findNavController(R.id.main_nav_host).navigateSafe(R.id.action_navProfile_to_navLogin)
                }
            }
    }

}