package com.boukouch.mini_projet.View

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.dao.NoteHelper
import com.boukouch.mini_projet.model.Note

class UpdateActivity : AppCompatActivity() {
    private lateinit var db : NoteHelper
    private lateinit var title_editEdit : EditText
    private lateinit var content_editEDIT : EditText
    private lateinit var editButton:ImageView
    private var noteId :Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        title_editEdit=findViewById(R.id.title_editEdit)
        content_editEDIT =findViewById(R.id.content_editEDIT)
        editButton=findViewById(R.id.editButton)


        db = NoteHelper(this)
        noteId=intent.getIntExtra("note_id"  , -1)
        if(noteId==-1){
            finish()
            return
        }

        val note = db.getNotebyId(noteId)
        title_editEdit.setText(note.title)
        content_editEDIT.setText(note.content)

        editButton.setOnClickListener{
            val newTitle = title_editEdit.text.toString()
            val newContent = content_editEDIT.text.toString()
            val updateNote = Note(newTitle, newContent)
            updateNote.id = noteId // Set the correct id

            db.updateNote(updateNote)

            finish()

            Toast.makeText(this, "changes saved", Toast.LENGTH_SHORT).show()
        }









    }
}