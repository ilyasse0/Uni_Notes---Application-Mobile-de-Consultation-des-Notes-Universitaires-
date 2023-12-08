package com.boukouch.mini_projet.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.boukouch.mini_projet.R
import com.boukouch.mini_projet.model.Matiere

class MatiereList(private val context: Activity, internal var matiere: List<Matiere>) : ArrayAdapter<Matiere>(context, R.layout.viewartists, matiere) {


    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.viewartists, null, true)

        val nom_matiere = listViewItem.findViewById(R.id.nom_matiere) as TextView
        val note = listViewItem.findViewById(R.id.note) as TextView
        val annee_unv = listViewItem.findViewById(R.id.annee_unv) as TextView
        val status = listViewItem.findViewById(R.id.status) as TextView

        val matiere = matiere[position]
        nom_matiere.text = matiere.nom_matiere
        note.text = matiere.note
        annee_unv.text = matiere.annee_unv
        status.text = matiere.status

        return listViewItem
    }
}