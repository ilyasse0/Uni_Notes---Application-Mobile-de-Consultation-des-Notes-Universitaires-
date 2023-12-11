package com.boukouch.mini_projet.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.accueille
import com.boukouch.mini_projet.adapter.Noteadapter
import com.boukouch.mini_projet.dao.NoteHelper
import com.google.android.material.navigation.NavigationView

class MainActivity_note : AppCompatActivity() {
    lateinit var AddButton : Button
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var db: NoteHelper
    private lateinit var noteAdapter : Noteadapter
    private lateinit var recyclerView: RecyclerView

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

}