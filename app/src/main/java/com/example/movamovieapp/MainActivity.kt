package com.example.movamovieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.movamovieapp.databinding.ActivityMainBinding
import com.example.movamovieapp.databinding.MovieItemBigBinding
import com.example.movamovieapp.utils.makeGone
import com.example.movamovieapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastClickTime: Long = 0
    private val DEBOUNCE_TIME = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomView()
        fragmentNavbarHidder()
    }

    private fun bottomView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime >= DEBOUNCE_TIME) {
                lastClickTime = currentTime
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            true
        }
    }

    private fun fragmentNavbarHidder() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.letYouInFragment,
                R.id.signUpFragment,
                R.id.welcomeFragment,
                R.id.signInFragment,
                R.id.splashFragment,
                R.id.top10AndNewReleaseFragment,
                R.id.movieDetailsFragment,
                R.id.youtubeVideoPlayerFragment -> {
                    binding.bottomNavigationView.makeGone()
                }
                else -> {
                    binding.bottomNavigationView.makeVisible()
                }
            }
        }
    }
}
