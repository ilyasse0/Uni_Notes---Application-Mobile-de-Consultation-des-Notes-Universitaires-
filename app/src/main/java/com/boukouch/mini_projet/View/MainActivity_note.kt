/*package com.boukouch.mini_projet.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boukouch.mini_projet.R
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




        AddButton.setOnClickListener {
            val intent = Intent(this, addNoteActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onResume() {
        super.onResume()
        noteAdapter.refreshData(db.getAllNotes())
    }

}*/