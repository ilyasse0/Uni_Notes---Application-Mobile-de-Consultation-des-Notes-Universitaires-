package com.boukouch.mini_projet.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.boukouch.mini_projet.R
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.adapter.MatiereList
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.MainActivity
import com.boukouch.mini_projet.Recuperation_mot_pass_Activity
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.accueille
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Etudiant
import com.boukouch.mini_projet.model.Matiere
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
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
    private var artistList: MutableList<Matiere>? = null
    private var nom: TextView? = null
    private var prenom: TextView? = null
    private var filiere: TextView? = null
    private var cne_etd: TextView? = null
    private var spinner: Spinner? = null
    lateinit var navView : NavigationView

    private var userNameTextView: TextView? = null
    private var userEmailTextView: TextView? = null
    private var image_profile: CircleImageView? = null
    companion object {
        private const val STATUS_SUCCESS = "success"
        private const val DATA_KEY = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val spinnerData = arrayOf("1","2","3","4","5","6")
        listView = findViewById(R.id.listViewArtists) as ListView
        artistList = mutableListOf<Matiere>()
        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        navView=findViewById(R.id.nav_view)
        filiere = findViewById(R.id.filier)
        cne_etd = findViewById(R.id.cne)
        spinner = findViewById<Spinner>(R.id.spiner) // Replace with your Spinner ID


        val headerView = navView?.getHeaderView(0)
        userNameTextView = headerView?.findViewById<TextView>(R.id.user_name)
        userEmailTextView = headerView?.findViewById<TextView>(R.id.email_aca)
        image_profile = headerView?.findViewById(R.id.image_profile)
        //userNameTextView?.text = "John Doe"
        // userEmailTextView?.text = "john.doe@example.com"




        val cne= LoginController.getUserCNE(this)
        fetchStudentData("$cne")
        // 3. Create an ArrayAdapter and set it to the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter

        // 4. Optionally, set an OnItemSelectedListener
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle the selected item here
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@Home, "Semestre$selectedItem", Toast.LENGTH_SHORT).show()
                loadNotes("$cne","$selectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }
        }


        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.Acceuil -> {Toast.makeText(this , "Acceuill" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, accueille::class.java)
                    startActivity(intent)
                }

                R.id.mail_academique -> {Toast.makeText(this , "Mail Académique" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, Recuperation_mot_pass_Activity::class.java)
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






    }
    fun loadNotes(cne: String,selectedItem:String) {
        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_selectnotes,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if (obj.getString("status") == "success") {
                        val array = obj.getJSONArray("data")
                        makeText(this, "Sucssfull", LENGTH_SHORT).show()

                        //Toast.makeText(this, "${array.toString()}", Toast.LENGTH_SHORT).show()
                        if (array.length() > 0) {
                            val array = obj.getJSONArray("data")

                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                val artist = Matiere(
                                    objectArtist.getString("nom_matier"),
                                    objectArtist.getString("resultat"),
                                    objectArtist.getString("annee_univ"),
                                    objectArtist.getString("status")
                                )
                               nom?.text=objectArtist.getString("nom")
                                prenom?.text=objectArtist.getString("prenom")
                                filiere?.text=objectArtist.getString("filiere")
                                cne_etd?.text=objectArtist.getString("cne")
                                val add = artistList!!.add(artist)
                                val adapter = MatiereList(this@Home, artistList!!)
                                listView!!.adapter = adapter
                            }
                            // nom?.setText("dhhhhh")
                        }
                    } else if(obj.getString("status") == "error") {
                        makeText(this, "Vide", LENGTH_SHORT).show()
                        artistList?.clear()

                        val artist = Matiere(
                            " ",
                            " ",
                            "",
                            ""
                        )
                        nom?.text = ""
                        prenom?.text = ""
                        filiere?.text = ""
                        cne_etd?.text = ""
                        artistList?.add(artist)

                        val adapter = MatiereList(this@Home, artistList!!)
                        listView?.adapter = adapter
                        //Toast.makeText(this, "${array.toString()}", Toast.LENGTH_SHORT).show()



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
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["cne"] = cne
                params["semestre"] = selectedItem
                return params
            }
        }

        // Adding request to the queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
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
                        Toast.makeText(this, "Ajiiiya", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }finally {
                    //setUiLoading(false)
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

