package com.boukouch.mini_projet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    fun onChangePasswordClick(view: View) {
        // Inflate the custom layout
        val changePasswordView = LayoutInflater.from(this).inflate(R.layout.layout_change_password,null)

        // Access the TextInputEditTexts in the custom layout
        val oldPasswordEditText = changePasswordView.findViewById<TextInputEditText>(R.id.editTextOldPassword)
        val newPasswordEditText = changePasswordView.findViewById<TextInputEditText>(R.id.editTextNewPassword)
        val repeatNewPasswordEditText = changePasswordView.findViewById<TextInputEditText>(R.id.editTextRepeatNewPassword)

        // Build the AlertDialog
        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(changePasswordView)
            .setPositiveButton("Changer") { dialog, which ->
                // Handle password change logic here
                val oldPassword = oldPasswordEditText.text.toString()
                val newPassword = newPasswordEditText.text.toString()
                val repeatNewPassword = repeatNewPasswordEditText.text.toString()

                // TODO: Implement your password change logic
                // For now, just print the values to the log
                println("Old Password: $oldPassword")
                println("New Password: $newPassword")
                println("Repeat New Password: $repeatNewPassword")
            }
            .setNegativeButton("Annuler", null) // Cancel button

        // Show the AlertDialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}