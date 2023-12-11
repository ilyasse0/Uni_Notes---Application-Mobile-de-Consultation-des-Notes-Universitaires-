package com.boukouch.mini_projet.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.model.Annonces
import com.boukouch.mini_projet.model.Matiere
import com.bumptech.glide.Glide

class AnnoncesList(private val context: Activity, internal var Annonces: List<Annonces>) : ArrayAdapter<Annonces>(context, R.layout.new_items, Annonces) {


    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.new_items, null, true)

        val date = listViewItem.findViewById(R.id.textViewDate) as TextView
        val titel = listViewItem.findViewById(R.id.textViewTitle) as TextView
        //val description = listViewItem.findViewById(R.id.textViewTitle) as TextView
        //val image = listViewItem.findViewById(R.id.imagesource) as ImageView

        val annonces = Annonces[position]
        date.text = annonces.Date
        titel.text = annonces.titel
        /*Glide.with(context)
            .load("https://kotlinboukouchtest.000webhostapp.com/annonce/upload/${annonces.image}")
            .placeholder(R.drawable.google) // Placeholder image while loading
            .error(R.drawable.google) // Error image if loading fails
            .into(image)*/


        return listViewItem
    }
}