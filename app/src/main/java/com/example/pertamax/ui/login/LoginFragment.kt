package com.example.pertamax.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import com.example.pertamax.R
import com.example.pertamax.utils.AlertUtils

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById<Button>(R.id.buttonLogin)
        val editTxt = view.findViewById<EditText>(R.id.editTextLoginPassword)
        val toggleBtn = view.findViewById<ImageView>(R.id.imageViewTogglePassword)

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
        val inputPassword = view?.findViewById<EditText>(R.id.editTextLoginPassword)
        val password = inputPassword?.text.toString().trim()

        if (password == "123456") {
            AlertUtils.showErrorDialog(requireContext(), "Password tidak sesuai, silakan dicoba kembali.")
        } else {
            // Navigate to HomeFragment after login
//            findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
            inputPassword?.text?.clear()
        }
    }

    private fun onToggleBtnClick(editText: EditText, toggleButton: ImageView) {
        if (editText.transformationMethod is android.text.method.PasswordTransformationMethod) {
            editText.transformationMethod = null
            toggleButton.setImageResource(R.drawable.ic_eye_off)
        } else {
            editText.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            toggleButton.setImageResource(R.drawable.ic_eye_on)
        }
        editText.setSelection(editText.text.length) // Keep cursor at the end
    }
}