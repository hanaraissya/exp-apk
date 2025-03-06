package com.example.pertamax.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pertamax.databinding.FragmentDashboardBinding
import androidx.navigation.fragment.findNavController
import com.example.pertamax.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.graphics.drawable.GradientDrawable
import android.text.InputType
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.text.method.PasswordTransformationMethod

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTxt = view.findViewById<EditText>(R.id.editTextLoginPassword)
        val toggleBtn = view.findViewById<ImageView>(R.id.imageViewTogglePassword)

        // Handle Toggle Button
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
            Toast.makeText(requireContext(), "Password is required!", Toast.LENGTH_SHORT).show()
        } else {
            // Show success message
            Toast.makeText(requireContext(), "Password Entered!, $password", Toast.LENGTH_SHORT).show()

            // Switch tab to Notifications
            val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNavView.selectedItemId = R.id.navigation_notifications

            // Clear input field after switching
            inputPassword.text.clear()
        }
    }

    private fun onToggleBtnClick(editText: EditText, toggleButton: ImageView) {
        if (editText.transformationMethod is PasswordTransformationMethod) {
            // Show password
            Toast.makeText(requireContext(), "SHOW PASSWORD", Toast.LENGTH_SHORT).show()
            editText.transformationMethod = null
            toggleButton.setImageResource(R.drawable.eye_off)
        } else {
            // Hide password
            Toast.makeText(requireContext(), "HIDE", Toast.LENGTH_SHORT).show()
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            toggleButton.setImageResource(R.drawable.eye_on)
        }
        editText.setSelection(editText.text.length) // Keep cursor at the end
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}