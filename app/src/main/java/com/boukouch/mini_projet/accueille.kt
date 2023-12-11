package com.boukouch.mini_projet

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.Adapter.AnnoncesList
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.View.Home
import com.boukouch.mini_projet.View.Profile
import com.boukouch.mini_projet.View.Recuperation_email_Activity
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Annonces
import com.boukouch.mini_projet.model.Etudiant
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject

class accueille : AppCompatActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null
    lateinit var toggle : ActionBarDrawerToggle
    private var listView : ListView? =null
    private var annoncelist: MutableList<Annonces>? = null
    private lateinit var loadingProgressBar: ProgressBar




   companion object {
        private const val STATUS_SUCCESS = "success"
        private const val DATA_KEY = "data"
    }
    private var userNameTextView: TextView? = null
    private var userEmailTextView: TextView? = null
    private var image_profile: CircleImageView? = null
    //lateinit var home: Home
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueille)

        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.nav_view)
        listView = findViewById(R.id.listViewAnnonce)
        annoncelist = mutableListOf<Annonces>()




        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout?.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val headerView = navView?.getHeaderView(0)

         userNameTextView = headerView?.findViewById<TextView>(R.id.user_name)
         userEmailTextView = headerView?.findViewById<TextView>(R.id.email_aca)
         image_profile = headerView?.findViewById(R.id.image_profile)
        //userNameTextView?.text = "John Doe"
       // userEmailTextView?.text = "john.doe@example.com"

        var cne= LoginController.getUserCNE(this)
        fetchStudentData("$cne")


        navView?.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.note -> {
                    val intent = Intent(this@accueille, Home::class.java)
                    startActivity(intent)
                }

                R.id.email -> {
                    val intent = Intent(this, Recuperation_email_Activity::class.java)
                    startActivity(intent)
                }

                R.id.profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                // R.id.nav_settings -> Toast.makeText(applicationContext , "Clicked Settings" , Toast.LENGTH_LONG).show()
                R.id.nav_Comte -> Toast.makeText(
                    applicationContext,
                    "Clicked login",
                    Toast.LENGTH_LONG
                ).show()

                R.id.nav_Password -> {
                    Toast.makeText(applicationContext, "Clicked login", Toast.LENGTH_LONG).show()
                }

                R.id.nav_share -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                }

                R.id.nav_feedback -> Toast.makeText(
                    applicationContext,
                    "Clicked FeedBack",
                    Toast.LENGTH_LONG
                ).show()

            }
            true
        }

       setUiLoading(true)
        fetchAnnouncements()



    }
    private fun setUiLoading(isLoading: Boolean) {
        loadingProgressBar.isVisible = isLoading
        listView?.isVisible = !isLoading
    }
    private fun fetchAnnouncements() {
        // Check network connectivity here if needed

        val stringRequest = object : StringRequest(
            Method.POST, EndPoints.link_select_annonces,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == STATUS_SUCCESS) {
                        val array = obj.getJSONArray(DATA_KEY)
                        for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                            val annonc = Annonces(
                                objectArtist.getString("titel"),
                                objectArtist.getString("description"),
                                objectArtist.getString("date"),
                                objectArtist.getString("files"),
                            )
                            annoncelist?.add(annonc)
                        }
                        val adapter = annoncelist?.let { AnnoncesList(this@accueille, it) }
                        listView?.adapter = adapter
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                } finally {
                    setUiLoading(false)
                }
            },
            Response.ErrorListener { volleyError ->
                volleyError.printStackTrace()
                setUiLoading(false)
            }) {}

        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    //fetch data
    fun fetchStudentData(cne: String) {
        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_profil,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == STATUS_SUCCESS) {
                        val array = obj.getJSONArray(DATA_KEY)
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
                                    objectArtist.getString("password_email_aca"),
                                    objectArtist.getString("profile")
                                )
                                userNameTextView?.setText("${etudiant.nom}_${etudiant.prenom}")
                                userEmailTextView?.setText(etudiant.email_aca)
                                if (!etudiant.profile.isNullOrBlank()) {
                                    Glide.with(this)
                                        .load("https://kotlinboukouchtest.000webhostapp.com/auth/upload/${etudiant.profile}")
                                        .placeholder(R.drawable.profile_default) // Replace with your placeholder image resource
                                        .error(R.drawable.profile_default) // Replace with your error image resource
                                        .into(image_profile!!)
                                } else {
                                    // Handle the case where the profile image URL is empty or null
                                }

                            }
                        }
                    } else {
                        Toast.makeText(this, "Ajiiiya", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }finally {
                    setUiLoading(false)
                }
            },

            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    // Handle error response
                    volleyError.printStackTrace()
                    setUiLoading(false)

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