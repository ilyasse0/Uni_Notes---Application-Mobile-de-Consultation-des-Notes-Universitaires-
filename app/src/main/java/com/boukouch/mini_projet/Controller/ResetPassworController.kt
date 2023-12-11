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
import com.boukouch.mini_projet.View.Home
import com.boukouch.mini_projet.View.NewPassword
import com.boukouch.mini_projet.View.VerificationCompte
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.accueille
import com.boukouch.mini_projet.data.EndPoints
import org.json.JSONException
import org.json.JSONObject

object ResetPassworController {

    //CheckEmail
    fun resetpassword(emailInput: EditText?,context: Context) {
        // Getting the record values
        val email = emailInput?.text.toString()

        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_checkemail,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if(obj.getString("status")=="success"){

                        val intent = Intent(context, VerificationCompte::class.java)
                        intent.putExtra("email", email)
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
                return params
            }
        }

        // Adding request to the queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }


    //VerificationCompte
    fun verifycompte(emailInput: EditText?,codeInput: EditText?,context: Context) {
        // Getting the record values
        val email = emailInput?.text.toString()
        val code = codeInput?.text.toString()

        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_verifycompte,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    if(obj.getString("status")=="success"){

                        val intent = Intent(context, NewPassword::class.java)
                        intent.putExtra("email", email)
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
                params["verifycode"] = code
                return params
            }
        }

        // Adding request to the queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }



    //NewPassword
    fun newpassword(emailInput: EditText?,password: EditText?,context: Context) {
        // Getting the record values
        val email = emailInput?.text.toString()
        val password = password?.text.toString()

        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_newpassword,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if(obj.getString("status")=="success"){

                        val intent = Intent(context, accueille::class.java)
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