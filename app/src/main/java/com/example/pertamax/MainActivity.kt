package com.example.pertamax

import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
    private lateinit var toolbar: View
    private var isFirstLaunch = true // Prevents unnecessary re-navigation to SplashFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupToolbar()
        setupDestinationListener()
        setupBackPressHandler()
    }

    /** Initialize Navigation Components */
    private fun setupNavigation() {
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_dashboard)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

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

    /** Setup Toolbar & QR Code Click Listener */
    private fun setupToolbar() {
        toolbar = findViewById(R.id.home_toolbar)
        val qrIcon = toolbar.findViewById<ImageView>(R.id.toolbar_qr_icon)
        qrIcon.setOnClickListener { navController.navigate(R.id.loginFragment) }
    }

    /** Show or Hide Bottom Nav & Toolbar based on the current destination */
    private fun setupDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldShowNavBar = destination.id in setOf(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_dashboard
            )

            if (shouldShowNavBar) {
                showToolbar(true)
                showBottomNav()
            } else {
                hideBottomNav()
                showToolbar(false)
                supportActionBar?.hide()
            }
        }
    }

    /** Handle Back Press Behavior */
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

    /** Navigate to Selected Destination */
    private fun navigateToDestination(itemId: Int): Boolean {
        if (navController.currentDestination?.id != itemId) {
            navController.navigate(itemId)
        }
        return true
    }

    /** Handle Logout Process */
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

    /** Show/Hide Bottom Navigation & Toolbar */
    private fun showBottomNav() { binding.navView.visibility = View.VISIBLE }
    private fun hideBottomNav() { binding.navView.visibility = View.GONE }
    private fun showToolbar(isVisible: Boolean) { toolbar.visibility = if (isVisible) View.VISIBLE else View.GONE }
}
