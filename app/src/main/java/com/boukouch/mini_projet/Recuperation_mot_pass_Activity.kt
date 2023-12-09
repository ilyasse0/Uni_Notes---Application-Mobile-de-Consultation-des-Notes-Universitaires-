package com.boukouch.mini_projet

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.SimpleFormatter

class Recuperation_mot_pass_Activity : AppCompatActivity() {

    private lateinit var date_input :EditText
    private lateinit var image_date_input : ImageView
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recuperation_mot_passe)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        date_input=findViewById(R.id.date_input)
        image_date_input=findViewById(R.id.date_image_input)

        val myCalendrier = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{view , year , month , dayofMonth ->

            myCalendrier.set(Calendar.YEAR,year)
            myCalendrier.set(Calendar.MONDAY , month)
            myCalendrier.set(Calendar.DAY_OF_MONTH , dayofMonth)
            updateLabel(myCalendrier)

        }

        image_date_input.setOnClickListener{
            DatePickerDialog(this  , datePicker , myCalendrier.get(Calendar.YEAR) , myCalendrier.get(Calendar.MONDAY) , myCalendrier.get(Calendar.DAY_OF_MONTH)).show()
            


        }




        toggle= ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.mail_academique -> {
                    Toast.makeText(this , "Mail Académique" , Toast.LENGTH_LONG).show()
                    val intent = Intent( this, Recuperation_mot_pass_Activity::class.java)
                    startActivity(intent)
                }
                R.id.nav_message -> {
                    val intent = Intent( this, Home::class.java)
                    startActivity(intent)
                }
                R.id.nav_Memo -> {
                    val intent = Intent(this, MainActivity_note::class.java)
                    startActivity(intent)
                }
                R.id.nav_settings -> Toast.makeText(applicationContext , "Clicked Settings" , Toast.LENGTH_LONG).show()
                R.id.nav_Comte -> Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()
                R.id.nav_Password -> {
                    Toast.makeText(applicationContext , "Clicked login" , Toast.LENGTH_LONG).show()


                }


                R.id.nav_share -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_feedback -> Toast.makeText(applicationContext , "Clicked FeedBack" , Toast.LENGTH_LONG).show()




            }
            true
        }

    }



private fun updateLabel(myCalendar: Calendar) {
    val myFormat = "dd-mm-yyyy"
    val sdf = SimpleDateFormat(myFormat , Locale.FRANCE)
    date_input.setText(sdf.format(myCalendar.time))

}

}