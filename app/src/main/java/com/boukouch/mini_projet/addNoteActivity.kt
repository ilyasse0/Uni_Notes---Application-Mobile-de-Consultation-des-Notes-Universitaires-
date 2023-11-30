package com.boukouch.mini_projet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.boukouch.mini_projet.dao.NoteHelper
import com.boukouch.mini_projet.databinding.ActivityAddNoteBinding
import com.boukouch.mini_projet.databinding.ActivityMainNoteBinding
import com.boukouch.mini_projet.model.Note

class addNoteActivity : AppCompatActivity() {

    private lateinit var binding :ActivityAddNoteBinding
    private lateinit var db :NoteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteHelper(this)

        binding.saveButton.setOnClickListener{
            val title =binding.titleEdit.text.toString()
            val content=binding.contentEDIT.text.toString()
            val note = Note(0 , title,content)
            db.insertNotes(note)
            finish()
            Toast.makeText(this, "its working", Toast.LENGTH_SHORT).show()
        }
        


    }
}