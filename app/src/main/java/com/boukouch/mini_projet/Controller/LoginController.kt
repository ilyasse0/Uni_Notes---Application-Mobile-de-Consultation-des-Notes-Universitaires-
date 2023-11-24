package com.boukouch.mini_projet.Controller

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.View.Home
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.data.EndPoints
import org.json.JSONException
import org.json.JSONObject


 object LoginController {

    fun login(emailInput: EditText?, passwordInput: EditText?, context: Context) {
        // Getting the record values
        val email = emailInput?.text.toString()
        val password = passwordInput?.text.toString()

        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_login,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if(obj.getString("status")=="success"){
                        saveUserEmail(email, context)
                        val intent = Intent(context, Home::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)


                    }
                    Toast.makeText(context.applicationContext, obj.getString("status"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context.applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["password"] = password
                return params
            }
        }

        // Adding request to the queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

     private fun saveUserEmail(email: String, context: Context) {
         val sharedPreferences: SharedPreferences =
             context.getSharedPreferences("PREF_NAME" , Context.MODE_PRIVATE)
         val editor: SharedPreferences.Editor = sharedPreferences.edit()

         editor.putString("KEY_USER_EMAIL", email)
         editor.apply()
     }

     fun getUserEmail(context: Context): String? {
         val sharedPreferences: SharedPreferences =
             context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)

         return sharedPreferences.getString("KEY_USER_EMAIL", null)
     }
}