package com.example.pertamax.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import com.example.pertamax.R

class SplashFragment : Fragment() {

    private val splashDuration = 3000L // Total 3 seconds
    private val logoOnlyDuration = 1000L // First 1 second

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views
        val progressBar = view.findViewById<View>(R.id.progress_bar)
        val progressWrapper = view.findViewById<View>(R.id.progress_wrapper)
        val overlay = view.findViewById<View>(R.id.overlay)

        // Initially hide loading and overlay
        progressWrapper.visibility = View.GONE
        overlay.visibility = View.GONE

        // Show loading and overlay after first second
        Handler(Looper.getMainLooper()).postDelayed({
            progressWrapper.visibility = View.VISIBLE
            startRotatingAnimation(progressBar)
            overlay.visibility = View.VISIBLE
        }, logoOnlyDuration)

        // Move to LoginFragment after 3 seconds
//        Handler(Looper.getMainLooper()).postDelayed({
//            if (isAdded && findNavController().currentDestination?.id == R.id.splashFragment) {
//                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
//            }
//        }, splashDuration)
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
