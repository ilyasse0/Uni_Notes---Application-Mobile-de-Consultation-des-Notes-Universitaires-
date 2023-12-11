package com.boukouch.mini_projet.View

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
import com.boukouch.mini_projet.Adapter.MatiereList
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Matiere
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val spinnerData = arrayOf("1","2","3","4","5","6")
        listView = findViewById(R.id.listViewArtists) as ListView
        artistList = mutableListOf<Matiere>()
        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        filiere = findViewById(R.id.filier)
        cne_etd = findViewById(R.id.cne)
        spinner = findViewById<Spinner>(R.id.spiner) // Replace with your Spinner ID

        val cne= LoginController.getUserCNE(this)
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


}

