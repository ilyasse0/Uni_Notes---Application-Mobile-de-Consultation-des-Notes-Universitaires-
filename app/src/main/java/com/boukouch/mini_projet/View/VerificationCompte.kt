package com.boukouch.mini_projet.View

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.boukouch.mini_projet.Controller.ResetPassworController
import com.boukouch.mini_projet.R

class VerificationCompte : AppCompatActivity() {

    private var email: EditText? = null
    private var verification_code: EditText? = null
    private var btn_send: Button? = null
    private var show_password: CheckBox? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_compte)

        val email_controller = intent.getStringExtra("email")
        email=findViewById<EditText>(R.id.EmailInput)
        verification_code=findViewById<EditText>(R.id.CodeInput)
        btn_send=findViewById<Button>(R.id.btn_send)
        show_password=findViewById<CheckBox>(R.id.show_password)

        email?.setText("$email_controller")

        btn_send?.setOnClickListener {
            ResetPassworController.verifycompte(email ,verification_code ,this)

        }
        show_password?.setOnCheckedChangeListener { _, isChecked ->
            // Toggle the password visibility based on the CheckBox state
            if (isChecked) {
                verification_code?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                verification_code?.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            // Move the cursor to the end of the text to update the display
            verification_code?.setSelection(verification_code!!.text.length)
        }


    }
}