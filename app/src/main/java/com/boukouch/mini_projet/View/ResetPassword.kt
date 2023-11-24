package com.boukouch.mini_projet.View

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.Controller.ResetPassworController
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.View.ResetPassword as ResetPassword1

class ResetPassword : AppCompatActivity() {

    private var back: Button? = null
    private var EmailInput: EditText? = null
    private var btn_send: Button? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        back = findViewById<Button>(R.id.back)
        EmailInput = findViewById<EditText>(R.id.EmailInput)
        btn_send = findViewById<Button>(R.id.btn_send)

        back?.setOnClickListener { finish() }

        btn_send?.setOnClickListener {

            val email = EmailInput?.text.toString()

            if (!email.isNullOrBlank()) {
                //loading?.visibility = View.VISIBLE
                ResetPassworController.resetpassword(EmailInput,this)
                //loading?.visibility = View.INVISIBLE

            } else {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }
    }


}