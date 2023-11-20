package com.boukouch.mini_projet.Controller

import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.Home
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
                        val intent = Intent(context, Home::class.java)
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
}