package com.boukouch.mini_projet.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.boukouch.mini_projet.model.Note

class NoteHelper(context : Context) :SQLiteOpenHelper(context , DATABASE_NAME,null , DATABASE_VERSION) {


    companion object{
        private const val DATABASE_NAME="Notes_umiProjet.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE="title"
        private const val COLUMN_CONTENT="content"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = (
                "CREATE TABLE $TABLE_NAME (" +
                        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$COLUMN_TITLE TEXT," +
                        "$COLUMN_CONTENT TEXT)"
                )
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
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



    fun getAllNotes(): ArrayList<Note> {
        val studentsList: ArrayList<Note> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME"
        val database = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = database.rawQuery(query, null)

            var id: Int
            var title: String
            var content: String
            var index: Int

            if (cursor.moveToFirst()) {
                do {
                    index = cursor.getColumnIndex("id")
                    id = cursor.getInt(index)
                    index = cursor.getColumnIndex("title")
                    title = cursor.getString(index)
                    index = cursor.getColumnIndex("content")
                    content = cursor.getString(index)

                    val student = Note(title, content)
                    student.id = id
                    studentsList.add(student)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return studentsList
    }












}