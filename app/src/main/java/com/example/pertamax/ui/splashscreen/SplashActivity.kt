package com.example.pertamax.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.pertamax.R
import com.example.pertamax.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashDuration = 3000L // Total 3 seconds
    private val logoOnlyDuration = 1000L // First 1 second

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Create activity_splash.xml similar to fragment_splash.xml

        // Find views
        val progressBar = findViewById<View>(R.id.progress_bar)
        val progressWrapper = findViewById<View>(R.id.progress_wrapper)
        val overlay = findViewById<View>(R.id.overlay)

        // Initially hide loading and overlay
        progressWrapper.visibility = View.GONE
        overlay.visibility = View.GONE

        // Show loading and overlay after first second
        Handler(Looper.getMainLooper()).postDelayed({
            progressWrapper.visibility = View.VISIBLE
            startRotatingAnimation(progressBar)
            overlay.visibility = View.VISIBLE
        }, logoOnlyDuration)

        // Move to LoginActivity after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close SplashActivity so the user cannot go back
        }, splashDuration)
    }

    private fun startRotatingAnimation(view: View) {
        val rotateAnimation = RotateAnimation(
            0f, 360f, // Rotate full circle
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000 // 1 second for full rotation
            interpolator = LinearInterpolator()
            repeatCount = Animation.INFINITE
        }
        view.startAnimation(rotateAnimation)
    }
}
