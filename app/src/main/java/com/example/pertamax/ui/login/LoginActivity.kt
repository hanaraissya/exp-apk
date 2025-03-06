package com.example.pertamax.ui.login

import android.view.View
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pertamax.MainActivity
import com.example.pertamax.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val editTxt = findViewById<EditText>(R.id.editTextLoginPassword)
        val toggleBtn = findViewById<ImageView>(R.id.imageViewTogglePassword)

        // Handle Toggle Button
        toggleBtn.setOnClickListener {
            onToggleBtnClick(editTxt, toggleBtn)
        }

        loginButton.setOnClickListener {
            onLoginBtnClick(it)
            // After login, move to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close LoginActivity
        }

    }

    private fun onLoginBtnClick(view: View) {
        val inputPassword = findViewById<EditText>(R.id.editTextLoginPassword)
        val password = inputPassword.text.toString().trim()

        if (password.isEmpty()) {
            inputPassword.error = "Password cannot be empty"
//            Toast.makeText(requireContext(), "Password is required!", Toast.LENGTH_SHORT).show()
        } else {
            // Show success message
//            Toast.makeText(requireContext(), "Password Entered!, $password", Toast.LENGTH_SHORT).show()

            // Switch tab to Notifications
//            val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
//            bottomNavView.selectedItemId = R.id.navigation_notifications

            // Clear input field after switching
            inputPassword.text.clear()
        }
    }

    private fun onToggleBtnClick(editText: EditText, toggleButton: ImageView) {
        if (editText.transformationMethod is PasswordTransformationMethod) {
            // Show password
//            Toast.makeText(requireContext(), "SHOW PASSWORD", Toast.LENGTH_SHORT).show()
            editText.transformationMethod = null
            toggleButton.setImageResource(R.drawable.eye_off)
        } else {
            // Hide password
//            Toast.makeText(requireContext(), "HIDE", Toast.LENGTH_SHORT).show()
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            toggleButton.setImageResource(R.drawable.eye_on)
        }
        editText.setSelection(editText.text.length) // Keep cursor at the end
    }

}
