package com.boukouch.mini_projet.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.boukouch.mini_projet.model.Note

class NoteHelper(context : Context) :SQLiteOpenHelper(context , DATABASE_NAME,null ,
    DATABASE_VERSION) {


    companion object{
        private const val DATABASE_NAME="Notes_umiProjet.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE="title"
        private const val COLUMN_CONTENT="content"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(("CREATE TABLE $DATABASE_NAME ("+

                "$COLUMN_ID INT PRIMARY KEY,"+
                "$COLUMN_TITLE TEXT,"+
                "$COLUMN_CONTENT TEXT)"
                ))    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $DATABASE_NAME")
        this.onCreate(db)
    }

    fun insertNotes(note: Note) : Long{
        val database = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID , note.id)
        values.put(COLUMN_TITLE , note.title)
        values.put(COLUMN_CONTENT , note.content)
        val succ = database.insert(TABLE_NAME , null , values )
        return succ
    }



    fun getAllNotes():List<Note> {
        val noteListe = mutableListOf<Note>()
        val db=readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query , null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = Note(id ,title,content)
            noteListe.add(note)
        }
        cursor.close()
        db.close()
        return noteListe
    }
}