
package com.boukouch.mini_projet.View



import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.boukouch.mini_projet.Controller.LoginController
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.R.layout
import com.boukouch.mini_projet.VolleySingleton
import com.boukouch.mini_projet.accueille
import com.boukouch.mini_projet.data.EndPoints
import com.boukouch.mini_projet.model.Etudiant
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

class Profile : AppCompatActivity() {

   private var textViewChangePassword: TextView? = null
   private var nom: EditText? = null
    private var prenom: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var change: TextView? = null
    private var container_1: LinearLayout? = null
    private var container_2: LinearLayout? = null
    private var racine: RelativeLayout? = null
    private var old_password: EditText? = null
    private var new_password: EditText? = null
    private var confirm_password: EditText? = null
    private var btn_save: Button? = null
    private var status: TextView? = null
    private var show_password: CheckBox? = null
    private lateinit var loadingProgressBar: ProgressBar



    companion object {
        private const val STATUS_SUCCESS = "success"
        private const val DATA_KEY = "data"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_profile)

        val cne= LoginController.getUserCNE(this)


        //setUiLoading(true)

        textViewChangePassword=findViewById(R.id.textViewChangePassword)
        prenom=findViewById(R.id.prenom)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        change=findViewById(R.id.textViewChangePassword)
        nom=findViewById(R.id.nom)
        change=findViewById(R.id.textViewChangePassword)
        container_1=findViewById(R.id.container_1)
        container_2=findViewById(R.id.container_2)
        racine=findViewById(R.id.racine)
        old_password=findViewById(R.id.old_password)
        new_password=findViewById(R.id.new_password)
        confirm_password=findViewById(R.id.confirm_password)
        btn_save=findViewById(R.id.btnsave)
        status=findViewById(R.id.status)
        show_password = findViewById<CheckBox>(R.id.show_password)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)




        btn_save?.setOnClickListener {
            val old_password = old_password?.text.toString()
            val new_password = new_password?.text.toString()
            val confirm_password = confirm_password?.text.toString()
            if(!old_password.isNullOrBlank() && !new_password.isNullOrBlank() && !confirm_password.isNullOrBlank()){
                if(new_password.toString()==confirm_password.toString()){
                    change_password("$cne")
                }
            }else{
                showSnackbar("Champes cannot be empty")
                //Toast.makeText(this, "Champes cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }


        password?.setText("********")


        textViewChangePassword?.setOnClickListener {
            container_2?.visibility = View.VISIBLE
            racine?.setBackgroundColor(Color.parseColor("#d1d1d1"))
        }
        container_1?.setOnClickListener{
            container_2?.visibility = View.GONE
            racine?.setBackgroundColor(Color.parseColor("#ffffff"))
        }

        show_password?.setOnCheckedChangeListener { _, isChecked ->
            // Toggle the password visibility based on the CheckBox state
            if (isChecked) {
                old_password?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                new_password?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                confirm_password?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                old_password?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                new_password?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                confirm_password?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // Move the cursor to the end of the text to update the display
            old_password?.setSelection(old_password!!.text.length)
            new_password?.setSelection(new_password!!.text.length)
            confirm_password?.setSelection(confirm_password!!.text.length)
        }

        setUiLoading(true)
        fetchStudentData("$cne")

    }

    private fun setUiLoading(isLoading: Boolean) {
        loadingProgressBar.isVisible = isLoading
       // racine?.isVisible = !isLoading
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
                                    objectArtist.getString("profile"),
                                    )

                                nom?.setText(etudiant.nom)
                                prenom?.setText(etudiant.prenom)
                                email?.setText(etudiant.email)
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
    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        )

        snackbar.show()
    }

    fun change_password(cne: String) {
        // Getting the record values
        val oldpassword = old_password?.text.toString()
        val newpassword = new_password?.text.toString()

        // Creating a volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.link_changepassword,
            Response.Listener<String> { response ->

                try {
                    val obj = JSONObject(response)
                    if(obj.getString("status")=="success"){
                        old_password?.setText("")
                        new_password?.setText("")
                        confirm_password?.setText("")
                        container_2?.visibility = View.GONE
                        racine?.setBackgroundColor(Color.parseColor("#ffffff"))
                        showSnackbar("Change Password Secussfull")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },

            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["cne"] = cne
                params["oldpassword"] = oldpassword
                params["newpassword"] = newpassword
                return params
            }
        }

        // Adding request to the queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)


    }

    fun onBackButtonClick(view: View) {
        // Create an Intent to start the activity_home.xml
        val intent = Intent(this, accueille::class.java)
        startActivity(intent)
    }


}
