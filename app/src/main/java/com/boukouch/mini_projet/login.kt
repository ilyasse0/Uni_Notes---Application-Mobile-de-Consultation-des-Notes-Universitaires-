package com.boukouch.mini_projet

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin=findViewById<Button>(R.id.btnLogin)
        val emailinput=findViewById<EditText>(R.id.EmailInput)
        val passwordinput=findViewById<EditText>(R.id.PasswordInput)

        //Login Button :
        btnLogin.setOnClickListener{
            val email:String =emailinput.text.toString()
            val pass:String=passwordinput.text.toString()
            val url:String="http://10.0.2.2/UMI_DB/auth/login.php"
            val params=HashMap<String,String>()
            params["email"]=email
            params["password"]=pass
            val jO= JSONObject(params as Map<*, *>)
            val rq:RequestQueue= Volley.newRequestQueue(this@login)
            val jor= JsonObjectRequest(Request.Method.POST,url,jO, Response.Listener { res->
                try {
                    if(res.getString("success").equals("1")){
                        val intent= Intent(this@login,MainActivity::class.java)
                        intent.putExtra("UserName",res.getString("user"))
                        startActivity(intent)
                        emailinput.text.clear()
                        passwordinput.text.clear()
                    } else { alert("Message d'Erreur !",res.getString("message")) }

                }catch (e:Exception){
                    alert("Message d'Erreur !",""+e.message)
                }
            },Response.ErrorListener { err->
                alert("Message d'Erreur !",""+err.message)
            })
            rq.add(jor)
        }





    }

    fun alert(title:String,message:String){
        val builder= AlertDialog.Builder(this@login)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok",{ dialogInterface: DialogInterface, i: Int -> }).create()
        builder.show()
    }
}