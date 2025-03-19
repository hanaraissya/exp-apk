package com.example.pertamax.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pertamax.R
import com.example.pertamax.databinding.ActivityDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.text.method.PasswordTransformationMethod

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup ViewModel
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        // Handle Toggle Button
        val editTxt = binding.editTextLoginPassword
        val toggleBtn = binding.imageViewTogglePassword

        toggleBtn.setOnClickListener {
            onToggleBtnClick(editTxt, toggleBtn)
        }

        // Handle Button Click
        binding.buttonLogin.setOnClickListener {
            onLoginBtnClick(it)
        }
    }

    private fun onLoginBtnClick(view: View) {
        val inputPassword = binding.editTextLoginPassword
        val password = inputPassword.text.toString().trim()

        if (password.isEmpty()) {
            inputPassword.error = "Password cannot be empty"
            Toast.makeText(this, "Password is required!", Toast.LENGTH_SHORT).show()
        } else {
            // Show success message
            Toast.makeText(this, "Password Entered! $password", Toast.LENGTH_SHORT).show()

            // Switch tab to Notifications (if BottomNavigationView exists)
            val bottomNavView = findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNavView?.selectedItemId = R.id.navigation_notifications

            // Clear input field after switching
            inputPassword.text.clear()
        }
    }

    private fun onToggleBtnClick(editText: EditText, toggleButton: ImageView) {
        if (editText.transformationMethod is PasswordTransformationMethod) {
            // Show password
            Toast.makeText(this, "SHOW PASSWORD", Toast.LENGTH_SHORT).show()
            editText.transformationMethod = null
            toggleButton.setImageResource(R.drawable.ic_eye_off)
        } else {
            // Hide password
            Toast.makeText(this, "HIDE", Toast.LENGTH_SHORT).show()
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            toggleButton.setImageResource(R.drawable.ic_eye_on)
        }
        editText.setSelection(editText.text.length) // Keep cursor at the end
    }
}
