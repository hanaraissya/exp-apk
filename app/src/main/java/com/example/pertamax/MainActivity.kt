package com.example.pertamax

import android.os.Bundle
import android.view.View
import android.view.Window
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_notifications, R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Start with SplashFragment
        if (savedInstanceState == null) {
            navController.navigate(R.id.splashFragment)
        }

        // Show/hide navbar and action bar based on destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in setOf(
                    R.id.navigation_home,
                    R.id.navigation_notifications,
                    R.id.navigation_dashboard
                )
            ) {
                println("Navigated to SHOW: ${destination.label}")
                showBottomNav()
                supportActionBar?.title = destination.label // Ensure ActionBar updates
            } else {
                println("Navigated to HIDE: ${destination.label}")
                hideBottomNav()
            }
        }

        binding.navView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_logout) {
                performLogout()
                true
            } else {
                handleNavigation(item.itemId, navController)
            }
        }

    }

    private fun handleNavigation(itemId: Int, navController: NavController): Boolean {
        return when (itemId) {
            R.id.navigation_home -> {
                navController.navigate(R.id.navigation_home)
                true
            }
            R.id.navigation_dashboard -> {
                navController.navigate(R.id.navigation_dashboard)
                true
            }
            R.id.navigation_notifications -> {
                navController.navigate(R.id.navigation_notifications)
                true
            }
            else -> false
        }
    }

    private fun performLogout() {
        getSharedPreferences("user_prefs", MODE_PRIVATE).edit().clear().apply()
        navigateAndClearBackStack(R.id.loginFragment)
    }

    private fun navigateAndClearBackStack(destinationId: Int) {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.mobile_navigation, true) // Clears back stack
            .build()
        navController.navigate(destinationId, null, navOptions)
    }

    fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
        supportActionBar?.show()
        println("SHOWING navbar & actionbar") // Debugging
    }

    fun hideBottomNav() {
        binding.navView.visibility = View.GONE
        supportActionBar?.hide()
        println("HIDING navbar & actionbar") // Debugging
    }
}