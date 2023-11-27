package com.boukouch.mini_projet.Controller

import android.widget.EditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Etudiant
import org.json.JSONException
import org.json.JSONObject

object ProfileController {
    fun fetchStudentData(cne: String, username: EditText?, etudiant: Etudiant) {
        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_profil,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val array = obj.getJSONArray("data")
                        if (array.length() > 0) {
                            val objectArtist = array.getJSONObject(0)
                            etudiant.apply {
                                var cne = objectArtist.getString("cne")
                                var nom = objectArtist.getString("nom")
                                var prenom = objectArtist.getString("prenom")
                                var email = objectArtist.getString("email")
                            }
                            username?.post {
                                username.setText(etudiant.nom)
                            }

                        }
                    } else {
                        // Handle failure
                        // Toast.makeText(context, "Ajiiiya", Toast.LENGTH_SHORT).show()
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
