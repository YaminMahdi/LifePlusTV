package com.life.plus.tv.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.life.plus.tv.R
import com.life.plus.tv.databinding.ActivityMainBinding
import com.life.plus.tv.utils.collectWithLifecycle
import com.life.plus.tv.utils.navigateSafe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupObserver()
        setupListener()

    }

    private fun setupUI() {

    }

    private fun setupObserver() {
        var userNull = null
        viewModel.currentUser.collectWithLifecycle {
            splashScreen.setKeepOnScreenCondition { false }
            if(it != null)
                findNavController(R.id.main_nav_host).navigateSafe(R.id.action_navLogin_to_navHome)
            else
                findNavController(R.id.main_nav_host).navigateSafe(R.id.action_navHome_to_navLogin)
        }
    }

    private fun setupListener() {
    }


}