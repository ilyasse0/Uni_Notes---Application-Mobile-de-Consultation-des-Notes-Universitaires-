package com.boukouch.mini_projet.View

import android.annotation.SuppressLint
import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.drawerlayout.widget.DrawerLayout
import com.boukouch.mini_projet.R
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.boukouch.mini_projet.Adapter.ArtistList
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Artist
import org.json.JSONException
import org.json.JSONObject

/*class Home : AppCompatActivity(){


    lateinit var toggle : ActionBarDrawerToggle
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}*/


class Home : AppCompatActivity() {

    private var listView: ListView? = null
    private var artistList: MutableList<Artist>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        listView = findViewById(R.id.listViewArtists) as ListView
        artistList = mutableListOf<Artist>()
        loadArtists("xxx")
    }

    /*private fun loadArtists() {
        val stringRequest = StringRequest(
            Request.Method.POST,
            EndPoints.link_selectnotes,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("status"), Toast.LENGTH_LONG).show()

                        val array = obj.getJSONArray("data")

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val artist = Artist(
                                objectArtist.getString("resultat"),
                                objectArtist.getString("nom_matier")
                            )
                            val add = artistList!!.add(artist)
                            val adapter = ArtistList(this@Home, artistList!!)
                            listView!!.adapter = adapter
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("status"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })
       val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)



    }*/
    fun loadArtists(cne: String) {
        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_selectnotes,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val array = obj.getJSONArray("data")
                        makeText(this, "${array.toString()}", LENGTH_SHORT).show()

                        //Toast.makeText(this, "${array.toString()}", Toast.LENGTH_SHORT).show()
                        if (array.length() > 0) {
                            val array = obj.getJSONArray("data")

                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val artist = Artist(
                                    objectArtist.getString("resultat"),
                                    objectArtist.getString("nom_matier")
                                )
                                val add = artistList!!.add(artist)
                                val adapter = ArtistList(this@Home, artistList!!)
                                listView!!.adapter = adapter
                            }
                            // nom?.setText("dhhhhh")
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

