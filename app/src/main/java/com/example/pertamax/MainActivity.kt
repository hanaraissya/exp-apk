package com.example.pertamax

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pertamax.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isFirstLaunch = true // Prevents unnecessary re-navigation to SplashFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        setupNavigation()
        setupDestinationListener()
        setupBackPressHandler()
    }

    /** Setup Bottom Navigation & Action Bar */
    private fun setupNavigation() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_notifications, R.id.navigation_home, R.id.navigation_dashboard)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        //Ensure SplashFragment is only loaded on the first launch
        if (isFirstLaunch) {
            isFirstLaunch = false
            navController.navigate(R.id.splashFragment)
        }

        binding.navView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_logout) {
                performLogout()
                true
            } else {
                navigateToDestination(item.itemId)
            }
        }
    }

    /** Show or Hide Bottom Nav & Action Bar based on the current destination */
    private fun setupDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldShowNavBar = destination.id in setOf(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_dashboard
            )

            if (shouldShowNavBar) {
                showBottomNav()
            } else {
                hideBottomNav()
                supportActionBar?.hide()
            }
        }
    }

    /** Handle Back Press: Prevent back navigation on certain fragments */
    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDestination = navController.currentDestination?.id
                val nonBackPressFragments = setOf(
                    R.id.loginFragment, R.id.navigation_home,
                    R.id.navigation_dashboard, R.id.navigation_notifications
                )

                if (currentDestination in nonBackPressFragments) {
                    // Stay on the current fragment (do nothing)
                } else {
                    navController.popBackStack()
                }
            }
        })
    }

    /** Handle Navigation Requests */
    private fun navigateToDestination(itemId: Int): Boolean {
        if (navController.currentDestination?.id != itemId) {
            navController.navigate(itemId)
        }
        return true
    }

    /** Perform Logout & Navigate to Login */
    private fun performLogout() {
        clearUserSession()
        navigateAndClearBackStack(R.id.loginFragment)
    }

    private fun clearUserSession() {
        getSharedPreferences("user_prefs", MODE_PRIVATE).edit().clear().apply()
    }

    private fun navigateAndClearBackStack(destinationId: Int) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mobile_navigation, true) // Clears back stack
            .build()
        navController.navigate(destinationId, null, navOptions)
    }

    /** Show/Hide Bottom Navigation & Action Bar */
    fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }
}