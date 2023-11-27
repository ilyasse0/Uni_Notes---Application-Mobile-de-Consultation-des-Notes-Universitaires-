package com.boukouch.mini_projet.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.boukouch.mini_projet.R
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat

class Home : AppCompatActivity(){

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val btnOpenDrawer: Button = findViewById(R.id.btnOpenDrawer)



        toggle= ActionBarDrawerToggle(this , drawerLayout , R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnOpenDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            Log.d("mesaaaaaaaaaage ", "Drawer opened")
        }
        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_home -> Toast.makeText(applicationContext , "Clicked Home" , Toast.LENGTH_LONG).show()
                R.id.nav_message -> Toast.makeText(applicationContext , "Clicked Message" , Toast.LENGTH_LONG).show()
                R.id.nav_Memo -> {}
                R.id.nav_settings -> Toast.makeText(applicationContext , "Clicked Settings" , Toast.LENGTH_LONG).show()
                R.id.nav_Comte -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_Password -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_share -> Toast.makeText(applicationContext , "Clicked Share" , Toast.LENGTH_LONG).show()
                R.id.nav_feedback -> Toast.makeText(applicationContext , "Clicked FeedBack" , Toast.LENGTH_LONG).show()




            }
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))

        {
            Log.d("mesa222222age ", "Drawer opened")
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}

