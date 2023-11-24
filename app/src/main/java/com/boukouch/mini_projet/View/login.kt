package com.boukouch.mini_projet.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.R.id.loading

class LoginActivity : AppCompatActivity() {
    private var emailinput: EditText? = null
    private var passwordinput: EditText? = null
    private var loading: ProgressBar? = null
    private var btnLogin: Button? = null
    private var forget_password: TextView? = null
    private var frgt_password: ImageView? = null
    private var show_password: CheckBox? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById<Button>(R.id.btnLogin)
        emailinput = findViewById<EditText>(R.id.EmailInput)
        passwordinput = findViewById<EditText>(R.id.PasswordInput)
        forget_password = findViewById<TextView>(R.id.forget_password)
        frgt_password = findViewById<ImageView>(R.id.frg_password)
        loading = findViewById<ProgressBar>(R.id.loading)
        show_password = findViewById<CheckBox>(R.id.show_password)

        btnLogin?.setOnClickListener {

            val email = emailinput?.text.toString()
            val password = passwordinput?.text.toString()

            if (!email.isNullOrBlank() && !password.isNullOrBlank()) {
                loading?.visibility = View.VISIBLE
                LoginController.login(emailinput, passwordinput, this)
                loading?.visibility = View.INVISIBLE

            } else {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }

        forget_password?.setOnClickListener {
            val intent = Intent(this@LoginActivity, ResetPassword::class.java)
            startActivity(intent)
        }
        frgt_password?.setOnClickListener {
            val intent = Intent(this@LoginActivity, ResetPassword::class.java)
            startActivity(intent)
        }

        show_password?.setOnCheckedChangeListener { _, isChecked ->
            // Toggle the password visibility based on the CheckBox state
            if (isChecked) {
                passwordinput?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                passwordinput?.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            // Move the cursor to the end of the text to update the display
            passwordinput?.setSelection(passwordinput!!.text.length)
        }
    }
}
