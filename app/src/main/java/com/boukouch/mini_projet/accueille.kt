package com.boukouch.mini_projet

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.Adapter.AnnoncesList
import com.boukouch.mini_projet.View.Home
import com.boukouch.mini_projet.View.Profile
import com.boukouch.mini_projet.View.Recuperation_email_Activity
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Annonces
import com.google.android.material.navigation.NavigationView
import org.json.JSONException
import org.json.JSONObject

class accueille : AppCompatActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    lateinit var toggle : ActionBarDrawerToggle
    private var listView : ListView? =null
    private var annoncelist: MutableList<Annonces>? = null

    //private var titel: TextView? = null
    //private var description: TextView? = null
    //private var date: TextView? = null
    //lateinit var home: Home
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueille)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView  = findViewById(R.id.nav_view)
        listView = findViewById(R.id.listViewAnnonce)
        annoncelist = mutableListOf<Annonces>()




        toggle= ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close)
        drawerLayout?.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


       navView?.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.note -> {
                    val intent = Intent( this, Home::class.java)
                    startActivity(intent)
                }
                R.id.email -> {
                    val intent = Intent( this, Recuperation_email_Activity::class.java)
                    startActivity(intent)
                }

                R.id.profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
               // R.id.nav_settings -> Toast.makeText(applicationContext , "Clicked Settings" , Toast.LENGTH_LONG).show()
                R.id.nav_Comte -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_Password -> {Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                }
                R.id.nav_share -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                }

                R.id.nav_feedback -> Toast.makeText(applicationContext , "Clicked FeedBack" , Toast.LENGTH_LONG).show()

            }
            true
        }
        annonce()
    }

    fun annonce() {
        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_select_annonces,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val array = obj.getJSONArray("data")
                        Toast.makeText(this, "Sucssfull", Toast.LENGTH_SHORT).show()

                        //Toast.makeText(this, "${array.toString()}", Toast.LENGTH_SHORT).show()
                        if (array.length() > 0) {
                            val array = obj.getJSONArray("data")

                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val annonc = Annonces(
                                    objectArtist.getString("titel"),
                                    objectArtist.getString("description"),
                                    objectArtist.getString("date"),
                                    objectArtist.getString("files"),
                                )

                                val add = annoncelist!!.add(annonc)
                                val adapter = AnnoncesList(this@accueille, annoncelist!!)
                                listView!!.adapter = adapter
                            }
                            // nom?.setText("dhhhhh")
                        }
                    } else {
                        Toast.makeText(this, "error ", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },

            object : Response.ErrorListener {override fun onErrorResponse(volleyError: VolleyError) {
                // Handle error response
                volleyError.printStackTrace()
            }
            }) {
        }

        // Adding request to the queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}