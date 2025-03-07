package com.example.pertamax.utils

import android.content.Context
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.pertamax.R

object AlertUtils {
    
    fun showErrorDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.alert_custom, null)

        // Set and message dynamically
        dialogView.findViewById<TextView>(R.id.textViewAlertMessage).text = message

        // Set up the dialog
        builder.setView(dialogView)
        val dialog = builder.create()

        // Set dialog position to the top
        val window = dialog.window
        if (window != null) {
            val params = window.attributes
            params.gravity = Gravity.TOP
            params.y = 200 // Adjust distance from the top (in pixels)
            window.attributes = params

            // Make background transparent
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        // Close button action
        dialogView.findViewById<ImageView>(R.id.imageViewCloseAlert).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}