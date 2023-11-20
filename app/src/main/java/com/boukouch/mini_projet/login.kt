package com.boukouch.mini_projet

import androidx.appcompat.app.AppCompatActivity

import  android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.data.EndPoints
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException



class LoginActivity : AppCompatActivity() {
      private var emailinput: EditText?=null
      private var passwordinput: EditText?=null
      private var txtview: TextView?=null
      private var btnLogin: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

         btnLogin=findViewById<Button>(R.id.btnLogin)
         txtview=findViewById<TextView>(R.id.txtview)
         emailinput=findViewById<EditText>(R.id.EmailInput)
         passwordinput=findViewById<EditText>(R.id.PasswordInput)

        btnLogin?.setOnClickListener {LoginController.login(emailinput, passwordinput,this)}




    }



    private fun login() {
        //getting the record values
        val email = emailinput?.text.toString()
        val password = passwordinput?.text.toString()

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_login,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("status"), Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("email", email)
                params.put("password", password)
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}