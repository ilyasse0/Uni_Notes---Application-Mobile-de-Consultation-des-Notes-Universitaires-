package com.boukouch.mini_projet.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.boukouch.mini_projet.Controller.ResetPassworController
import com.boukouch.mini_projet.R

class NewPassword : AppCompatActivity() {
   private var EmailInput: EditText?=null
    private var PasswordInput:EditText?=null
    private var ConfirmPasswordInput:EditText?=null
    private var show_password: CheckBox?=null
    private var btnLogin: Button?=null
    private var loading: ProgressBar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        EmailInput=findViewById<EditText>(R.id.EmailInput)
        PasswordInput=findViewById<EditText>(R.id.PasswordInput)
        ConfirmPasswordInput=findViewById<EditText>(R.id.ConfirmPasswordInput)
        show_password=findViewById<CheckBox>(R.id.show_password)
        btnLogin=findViewById<Button>(R.id.btnLogin)


        //SEND
       btnLogin?.setOnClickListener {

            val email = EmailInput?.text.toString()
            val password = PasswordInput?.text.toString()
            val confirmpassword = ConfirmPasswordInput?.text.toString()

            if (!email.isNullOrBlank() && !password.isNullOrBlank() && !confirmpassword.isNullOrBlank()) {
                loading?.visibility = View.VISIBLE
                ResetPassworController.newpassword(EmailInput,PasswordInput,this)
                loading?.visibility = View.INVISIBLE

            } else {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }

        //Show_Password
       show_password?.setOnCheckedChangeListener { _, isChecked ->
            // Toggle the password visibility based on the CheckBox state
            if (isChecked) {
                PasswordInput?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ConfirmPasswordInput?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                PasswordInput?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ConfirmPasswordInput?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            // Move the cursor to the end of the text to update the display
            PasswordInput?.setSelection(PasswordInput!!.text.length)
            ConfirmPasswordInput?.setSelection(ConfirmPasswordInput!!.text.length)
        }
    }
}