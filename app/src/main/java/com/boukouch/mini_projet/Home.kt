package com.boukouch.mini_projet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import android.annotation.SuppressLint

class Home : AppCompatActivity(){

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)



        toggle= ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.mail_academique ->{Toast.makeText(this , "Mail Académique" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, Recuperation_mot_pass_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_message -> {
                    val intent = Intent(this@Home, Home::class.java)
                    startActivity(intent)
                }
                R.id.nav_Memo -> {
                    val intent = Intent(this@Home, MainActivity_note::class.java)
                    startActivity(intent)
                    Log.d("Home", "memeo Home")
                }
                R.id.nav_settings -> Toast.makeText(applicationContext , "Clicked Settings" , Toast.LENGTH_LONG).show()
                R.id.nav_Comte -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_Password -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_share -> {
                    val intent = Intent(this@Home, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_feedback -> Toast.makeText(applicationContext , "Clicked FeedBack" , Toast.LENGTH_LONG).show()




            }
            true
        }






    }






}

