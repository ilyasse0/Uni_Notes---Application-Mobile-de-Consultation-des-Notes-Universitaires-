package com.boukouch.mini_projet.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.adapter.Noteadapter
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.accueille
import com.boukouch.mini_projet.dao.NoteHelper
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Etudiant
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject

class MainActivity_note : AppCompatActivity() {
    lateinit var AddButton : Button
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var db: NoteHelper
    private lateinit var noteAdapter : Noteadapter
    private lateinit var recyclerView: RecyclerView
    companion object {
        private const val STATUS_SUCCESS = "success"
        private const val DATA_KEY = "data"
    }
    private var userNameTextView: TextView? = null
    private var userEmailTextView: TextView? = null
    private var image_profile: CircleImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_note)
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        recyclerView = findViewById(R.id.notesRecycleView)
        AddButton = findViewById(R.id.Addbutton)
        db = NoteHelper(this)

        noteAdapter = Noteadapter(emptyList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val headerView = navView?.getHeaderView(0)
        userNameTextView = headerView?.findViewById<TextView>(R.id.user_name)
        userEmailTextView = headerView?.findViewById<TextView>(R.id.email_aca)
        image_profile = headerView?.findViewById(R.id.image_profile)

        var cne= LoginController.getUserCNE(this)
        fetchStudentData("$cne")
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {


                R.id.Acceuil -> {Toast.makeText(this , "Acceuill" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, accueille::class.java)
                    startActivity(intent)
                }

                R.id.mail_academique -> {Toast.makeText(this , "Mail Académique" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, Recuperation_email_Activity::class.java)
                    startActivity(intent)
                }
                R.id.note -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                }
                R.id.nav_Memo -> {
                    val intent = Intent(this, MainActivity_note::class.java)
                    startActivity(intent)

                }
                R.id.profile -> {Toast.makeText(applicationContext, "Clicked Settings", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }

                R.id.logout -> {
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            true
        }


        AddButton.setOnClickListener {
            val intent = Intent(this, addNoteActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onResume() {
        super.onResume()
        noteAdapter.refreshData(db.getAllNotes())
    }
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
                        Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }finally {
                   // setUiLoading(false)
                }
            },

            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    // Handle error response
                    volleyError.printStackTrace()
                    //setUiLoading(false)

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