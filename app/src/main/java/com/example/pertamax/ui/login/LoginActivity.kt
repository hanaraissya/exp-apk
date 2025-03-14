package com.example.pertamax.ui.login

import android.view.View
import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import com.example.pertamax.MainActivity
import com.example.pertamax.R
import com.example.pertamax.utils.AlertUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val editTxt = findViewById<EditText>(R.id.editTextLoginPassword)
        val toggleBtn = findViewById<ImageView>(R.id.imageViewTogglePassword)

        // Set up Toolbar as ActionBar
        val toolbar: Toolbar = findViewById(R.id.loginToolbar)
        setSupportActionBar(toolbar)

        // Set up LoginButton
        loginButton.isEnabled = false
        editTxt.addTextChangedListener { text ->
            loginButton.isEnabled = text?.length == 6
        }

        // Handle Toggle Button
        toggleBtn.setOnClickListener {
            onToggleBtnClick(editTxt, toggleBtn)
        }

        loginButton.setOnClickListener {
            onLoginBtnClick(it)
        }

    }

    private fun onLoginBtnClick(view: View) {
        val inputPassword = findViewById<EditText>(R.id.editTextLoginPassword)
        val password = inputPassword.text.toString().trim()

        if (password == "123456") {
            AlertUtils.showErrorDialog(this, "Password tidak sesuai, silakan dicoba kembali.")
        } else {
            // After login, move to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close LoginActivity
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
