package com.boukouch.mini_projet


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.adapter.AnnoncesList
import com.boukouch.mini_projet.View.Home
import com.boukouch.mini_projet.View.LoginActivity
import com.boukouch.mini_projet.View.MainActivity_note
import com.boukouch.mini_projet.View.Profile
import com.boukouch.mini_projet.View.Recuperation_email_Activity
import com.boukouch.mini_projet.View.ResetPassword
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Annonces
import com.google.android.material.navigation.NavigationView
import org.json.JSONException
import org.json.JSONObject

class accueille : AppCompatActivity() {


    private var drawerLayout: DrawerLayout? = null
    lateinit var navView : NavigationView
    lateinit var toggle : ActionBarDrawerToggle
    private var listView : ListView? =null
    private var annoncelist: MutableList<Annonces>? = null
    private lateinit var loadingProgressBar: ProgressBar


    companion object {
        private const val STATUS_SUCCESS = "success"
        private const val DATA_KEY = "data"
    }
    private var titel: TextView? = null
    private var description: TextView? = null
    private var date: TextView? = null
    lateinit var home: Home




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueille)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        listView = findViewById(R.id.listViewAnnonce)
        navView=findViewById(R.id.nav_view)
        annoncelist = mutableListOf<Annonces>()


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout?.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.Acceuil -> {Toast.makeText(this , "Acceuill" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, accueille::class.java)
                    startActivity(intent)
                }

                R.id.mail_academique -> {Toast.makeText(this , "Mail Académique" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, Recuperation_email_Activity::class.java)
                    startActivity(intent)
                }
                R.id.note -> {
                    val intent = Intent( this, Home::class.java)
                    startActivity(intent)
                }
                R.id.nav_Memo -> {
                    val intent = Intent(this, MainActivity_note::class.java)
                    startActivity(intent)
                }
                R.id.profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext , "Clicked Settings" , Toast.LENGTH_LONG).show()}



                R.id.logout -> {
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }







            }
            true
        }
        fetchAnnouncements()
        setUiLoading(true)















    }

    private fun setUiLoading(isLoading: Boolean) {
        loadingProgressBar.isVisible = isLoading
        listView?.isVisible = !isLoading
    }



    private fun fetchAnnouncements() {
        // Check network connectivity here if needed

        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_select_annonces,
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







}