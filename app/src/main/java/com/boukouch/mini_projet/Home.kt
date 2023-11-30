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

class Home : AppCompatActivity(){

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
       // val btnOpenDrawer: Button = findViewById(R.id.btnOpenDrawer)



        toggle= ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*
        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_home -> Log.d("Home", "Clicked Home")
                R.id.nav_message -> Toast.makeText(this , "Clicked Message" , Toast.LENGTH_LONG).show()
                R.id.nav_Memo -> {
                    val intent = Intent(this@Home, MainActivity_note::class.java)
                    startActivity(intent)
                }
                R.id.nav_settings -> Toast.makeText(applicationContext , "Clicked Settings" , Toast.LENGTH_LONG).show()
                R.id.nav_Comte -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_Password -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_share -> Toast.makeText(applicationContext , "Clicked Share" , Toast.LENGTH_LONG).show()
                R.id.nav_feedback -> Toast.makeText(applicationContext , "Clicked FeedBack" , Toast.LENGTH_LONG).show()




            }
            true
        }

         */

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle the "Home" item click
                    // For example, you can show a toast or start a new activity
                    Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_LONG).show()
                    // If you want to start a new activity, you can use an Intent
                    // val intent = Intent(this@YourCurrentActivity, YourDestinationActivity::class.java)
                    // startActivity(intent)
                }
                // Add cases for other menu items if needed
                R.id.nav_message -> {
                    // Handle the "Message" item click
                    // For example, show a toast or start a new activity
                    Toast.makeText(applicationContext, "Clicked Message", Toast.LENGTH_LONG).show()
                }
                R.id.nav_Memo -> {
                    // Handle the "Memo" item click
                    // For example, start a new activity
                    val intent = Intent(this, MainActivity_note::class.java)
                    startActivity(intent)
                }
                // Add other cases for additional menu items
                // ...
            }
            // Return true to indicate that the item click has been handled
            true
        }


    }






}

