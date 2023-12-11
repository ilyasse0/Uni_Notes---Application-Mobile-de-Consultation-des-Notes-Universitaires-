package com.boukouch.mini_projet.View

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.boukouch.mini_projet.R
import com.bumptech.glide.Glide

class Annonce_Detaille : AppCompatActivity() {

    private var image_annonce: ImageView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annonce_detaille)

        val image = intent.getStringExtra("image")
        image_annonce=findViewById(R.id.image_annonce)

        if (!image.isNullOrBlank()) {
            Glide.with(this)
                .load("https://kotlinboukouchtest.000webhostapp.com/annonce/upload/$image")
                .placeholder(R.drawable.background_progress) // Replace with your placeholder image resource
                .error(R.drawable.background_progress) // Replace with your error image resource
                .into(image_annonce!!)
        } else {

            // Handle the case where the image URL is empty or null
        }


        /*if (!annonces.image.isNullOrBlank()) {
            Glide.with(this)
                .load("https://kotlinboukouchtest.000webhostapp.com/annonce/upload/${annonces.image}")
                .placeholder(R.drawable.profile_default) // Replace with your placeholder image resource
                .error(R.drawable.profile_default) // Replace with your error image resource
                .into(image_profile!!)
        } else {
            // Handle the case where the profile image URL is empty or null
        }*/

    }
}