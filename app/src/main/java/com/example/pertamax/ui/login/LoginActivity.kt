package com.example.pertamax.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.pertamax.R
import com.example.pertamax.utils.AlertUtils
import android.content.Intent
import android.text.method.PasswordTransformationMethod
import com.example.pertamax.MainActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val editTxt = findViewById<EditText>(R.id.editTextLoginPassword)
        val toggleBtn = findViewById<ImageView>(R.id.imageViewTogglePassword)

        // Disable login button initially
        loginButton.isEnabled = false
        editTxt.addTextChangedListener { text ->
            loginButton.isEnabled = text?.length == 6
        }

        // Handle Toggle Button
        toggleBtn.setOnClickListener {
            onToggleBtnClick(editTxt, toggleBtn)
        }

        loginButton.setOnClickListener {
            onLoginBtnClick()
        }
    }

    private fun onLoginBtnClick() {
        val inputPassword = findViewById<EditText>(R.id.editTextLoginPassword)
        val password = inputPassword.text.toString().trim()

        if (password == "123456") {
            AlertUtils.showErrorDialog(this, "Password tidak sesuai, silakan dicoba kembali.")
        } else {
            inputPassword?.text?.clear()
            // Navigate to HomeActivity after login
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun onToggleBtnClick(editText: EditText, toggleButton: ImageView) {
        if (editText.transformationMethod is PasswordTransformationMethod) {
            editText.transformationMethod = null
            toggleButton.setImageResource(R.drawable.ic_eye_off)
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            toggleButton.setImageResource(R.drawable.ic_eye_on)
        }
        editText.setSelection(editText.text.length) // Keep cursor at the end
    }
}
