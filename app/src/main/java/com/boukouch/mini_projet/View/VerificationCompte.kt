package com.boukouch.mini_projet.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.boukouch.mini_projet.Controller.ResetPassworController
import com.boukouch.mini_projet.R

class VerificationCompte : AppCompatActivity() {

    private var email: EditText? = null
    private var verification_code: EditText? = null
    private var btn_send: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_compte)

        val email_controller = intent.getStringExtra("email")
        email=findViewById<EditText>(R.id.EmailInput)
        verification_code=findViewById<EditText>(R.id.CodeInput)
        btn_send=findViewById<Button>(R.id.btn_send)

        email?.setText("$email_controller")

        btn_send?.setOnClickListener {
            ResetPassworController.verifycompte(email ,verification_code ,this)

        }


    }
}