package com.boukouch.mini_projet.View

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.Controller.ProfileController
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Etudiant
import org.json.JSONException
import org.json.JSONObject

class Recuperation_email_Activity : AppCompatActivity() {
    private var email_aca:TextView?=null
    private var password:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recuperation_email)
        email_aca=findViewById(R.id.email_aca)
        password=findViewById(R.id.password)
        val cne= LoginController.getUserCNE(this)

        fetchStudentData("$cne")
    }

    fun fetchStudentData(cne: String) {
        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_profil,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val array = obj.getJSONArray("data")
                        //Toast.makeText(this, "${array.toString()}", Toast.LENGTH_SHORT).show()
                        if (array.length() > 0) {
                            val array = obj.getJSONArray("data")

                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val etudiant = Etudiant(
                                    objectArtist.getString("nom"),
                                    objectArtist.getString("prenom"),
                                    objectArtist.getString("email"),
                                    objectArtist.getString("cne"),
                                    objectArtist.getString("email_acadimic"),
                                    objectArtist.getString("password_email_aca"))

                                email_aca?.setText(etudiant.email_aca)
                                password?.setText(etudiant.password_email_aca)
                            }
                        }
                    } else {
                        Toast.makeText(this, "Ajiiiya", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },

            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    // Handle error response
                    volleyError.printStackTrace()
                }
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["cne"] = cne
                return params
            }
        }
        // Adding request to the queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }


}