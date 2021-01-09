package com.example.daily_workout_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION ){

    companion object{
        private val DATABASE_VERSION = 1//DATABASE version
        private val DATABASE_NAME = "TenMinutesWorkout.db"//Name of the DATABASE
        private val TABLE_HISTORY = "history" //Table Name
        private val COLUMN_ID = "_id" //Column ID is Primary Key
        private val COLUMN_COMPLETED_DATE = "completed_date"//Column for Completion Date
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating the history table
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" +
                COLUMN_ID+ " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE +
                " TEXT)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //dropping the database table and recreating new updated table with
        //updated version
        db?.execSQL("DROP TABLE EXISTS"+ TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date: String){

        val values = ContentValues()//Creates an empty set of values using the default initial size
        values.put(COLUMN_COMPLETED_DATE, date)
        val db = this.writableDatabase//getting the writeabledatabase
        db.insert(TABLE_HISTORY, null,values)//inserting the updated date in column
        db.close()//closing the database
    }
}

