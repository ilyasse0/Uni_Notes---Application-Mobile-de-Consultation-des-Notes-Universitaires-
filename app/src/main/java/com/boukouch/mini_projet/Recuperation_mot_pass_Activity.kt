package com.boukouch.mini_projet

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.SimpleFormatter

class Recuperation_mot_pass_Activity : AppCompatActivity() {

    private lateinit var date_input :EditText
    private lateinit var image_date_input : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recuperation_mot_passe)

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

    }



private fun updateLabel(myCalendar: Calendar) {
    val myFormat = "dd-mm-yyyy"
    val sdf = SimpleDateFormat(myFormat , Locale.FRANCE)
    date_input.setText(sdf.format(myCalendar.time))

}

}