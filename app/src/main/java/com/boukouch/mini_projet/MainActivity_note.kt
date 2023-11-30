package com.boukouch.mini_projet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.boukouch.mini_projet.adapter.Noteadapter
import com.boukouch.mini_projet.dao.NoteHelper
import com.boukouch.mini_projet.databinding.ActivityMainBinding
import com.boukouch.mini_projet.databinding.ActivityMainNoteBinding

class MainActivity_note : AppCompatActivity() {
    private lateinit var binding :ActivityMainNoteBinding
    private lateinit var db: NoteHelper
    private lateinit var noteAdapter :Noteadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= NoteHelper(this)
        noteAdapter= Noteadapter(db.getAllNotes(),this)

         binding.notesRecycleView.layoutManager=LinearLayoutManager(this)
        binding.notesRecycleView.adapter=noteAdapter

        binding.AddButton.setOnClickListener{
            Log.d(" debuging" , "this is cheeking for the log" )
            val intent = Intent(this , addNoteActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onResume() {
        super.onResume()
        noteAdapter.refreshData(db.getAllNotes())

    }


}