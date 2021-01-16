package com.example.daily_workout_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
/**
 * SqliteOpenHelper:
 *
 * Create a helper object to create, open, and/or manage a database.
 * The database is not actually created or opened until one of
 * {@link #getWritableDatabase} or {@link #getReadableDatabase} is called.
 *
 */

class SqliteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION ){

//    to write a function or any member of the class that can be called
//    without having the instance of the class then you can write the
//    same as a member of a companion object inside the class.

    companion object{
        private val DATABASE_VERSION = 1//DATABASE version
        private val DATABASE_NAME = "TenMinutesWorkout.db"//Name of the DATABASE
        private val TABLE_HISTORY = "history" //Table Name
        private val COLUMN_ID = "_id" //Column ID is Primary Key
        private val COLUMN_COMPLETED_DATE = "completed_date"//Column for Completion Date
    }

//    To create a Database Table
    override fun onCreate(db: SQLiteDatabase?) {
        //creating the history table
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" +
                COLUMN_ID+ " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE +
                " TEXT)")
        db?.execSQL(CREATE_EXERCISE_TABLE)
    }

//    To Upgrade the Database with new log entry
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //dropping the database table and recreating new updated table with
        //updated version
        db?.execSQL("DROP TABLE IF EXISTS" + TABLE_HISTORY)
        onCreate(db)
    }

    //method to add date into the TABLE_HISTORY database
    fun addDate(date: String){
        val values = ContentValues()  //Creates an empty set of values using the default initial size
        values.put(COLUMN_COMPLETED_DATE, date)
        val db = this.writableDatabase //creating a writeabledatabase
        db.insert(TABLE_HISTORY, null,values)  //inserting the updated date in column
        db.close()//closing the database
    }

    //method to return list of completedDates
    //Cursors are what contain the result set of a query made against a database.
    fun getAllCompletedDatesList() : ArrayList<String>{
        val list = ArrayList<String>()
        val db = this.readableDatabase   //creating a readable database
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY",null)
        //moveToNext() method will return false if the cursor is already past the last entry in
        // the result set.
        while(cursor.moveToNext()){
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
            list.add(dateValue)
        }

        cursor.close()//Closes the Cursor, releasing all of its resources and making it completely invalid.
        return list
    }
}

