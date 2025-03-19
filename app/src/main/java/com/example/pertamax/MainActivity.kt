package com.example.pertamax

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pertamax.databinding.ActivityMainBinding
import com.example.pertamax.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toolbar: View

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
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_dashboard)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

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
        toolbar = findViewById(R.id.toolbar_home)
        val qrIcon = toolbar.findViewById<ImageView>(R.id.toolbar_qr_icon)
        qrIcon.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    /** Show or Hide Bottom Nav & Toolbar based on the current destination */
    private fun setupDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldShowNavBar = destination.id in setOf(
                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_dashboard
            )

            if (shouldShowNavBar) {
                supportActionBar?.hide()
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
                    R.id.navigation_home,
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
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun clearUserSession() {
        getSharedPreferences("user_prefs", MODE_PRIVATE).edit().clear().apply()
    }

    /** Show/Hide Bottom Navigation & Toolbar */
    private fun showBottomNav() { binding.navView.visibility = View.VISIBLE }
    private fun hideBottomNav() { binding.navView.visibility = View.GONE }
    private fun showToolbar(isVisible: Boolean) { toolbar.visibility = if (isVisible) View.VISIBLE else View.GONE }
}