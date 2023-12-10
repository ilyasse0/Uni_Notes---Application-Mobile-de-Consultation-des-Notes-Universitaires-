package com.boukouch.mini_projet.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.View.UpdateActivity
import com.boukouch.mini_projet.dao.NoteHelper
import com.boukouch.mini_projet.model.Note

class Noteadapter (private var notes:List<Note> , context:Context): RecyclerView.Adapter<Noteadapter.NoteViewHolder>(){

private val db:NoteHelper = NoteHelper(context)
    class NoteViewHolder(note_item :View) :RecyclerView.ViewHolder(note_item){
        val titleTextView:TextView=note_item.findViewById(R.id.titleTextView)
        val contentTextView :TextView=note_item.findViewById(R.id.contentTextView)
        val updateBtn : ImageView=note_item.findViewById(R.id.updatebtn)
        val deleteeBtn : ImageView=note_item.findViewById(R.id.deleteebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.note_item , parent ,false )
        return NoteViewHolder(view)


    }

    override fun getItemCount(): Int =notes.size




    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note=notes[position]
        holder.titleTextView.text=note.title
        holder.contentTextView.text=note.content


          holder.updateBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteeBtn.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context , "Note deleted" , Toast.LENGTH_SHORT).show()
        }






    }
    fun refreshData(newNotes :List<Note>){
        notes=newNotes
        notifyDataSetChanged()

    }
}