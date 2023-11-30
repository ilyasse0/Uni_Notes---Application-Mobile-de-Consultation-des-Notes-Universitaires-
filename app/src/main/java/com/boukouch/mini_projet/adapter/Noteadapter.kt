package com.boukouch.mini_projet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.model.Note

class Noteadapter (private var notes:List<Note> , context:Context): RecyclerView.Adapter<Noteadapter.NoteViewHolder>(){
    class NoteViewHolder(note_item :View) :RecyclerView.ViewHolder(note_item){
        val titleTextView:TextView=note_item.findViewById(R.id.titleTextView)
        val contentTextView :TextView=note_item.findViewById(R.id.contentTextView)
        class NoteViewHolder(note_item: View):RecyclerView.ViewHolder(note_item){


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.note_item , parent ,false )
        return NoteViewHolder(view)


    }

    override fun getItemCount(): Int =notes.size




    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note=notes[position]
        holder.titleTextView.text=note.title
        holder.contentTextView.text=note.content


    }
    fun refreshData(newNotes :List<Note>){
        notes=newNotes
        notifyDataSetChanged()

    }
}