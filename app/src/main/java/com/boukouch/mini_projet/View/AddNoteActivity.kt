package com.boukouch.mini_projet.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.dao.NoteHelper
import com.boukouch.mini_projet.model.Note

class addNoteActivity : AppCompatActivity() {

    private lateinit var db :NoteHelper
    private lateinit var saveButton :ImageView
    private lateinit var Editbutton :ImageView
    private lateinit var title:EditText
    private lateinit var content :EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        saveButton=findViewById(R.id.saveButton)
        title=findViewById(R.id.titleEdit)
        content=findViewById(R.id.contentEDIT)
        db = NoteHelper(this)


        saveButton.setOnClickListener {
            val title = title.text.toString()
            val content = content.text.toString()
            val note = Note( title, content)
            db.insertNotes(note)
            finish()
            Toast.makeText(this, "New note added", Toast.LENGTH_SHORT).show()
        }


       


        


    }
}