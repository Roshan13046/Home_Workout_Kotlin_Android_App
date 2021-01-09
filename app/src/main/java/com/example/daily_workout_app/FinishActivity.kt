package com.example.daily_workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbar_finish_activity)
        val actionbar = supportActionBar //actionbar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true) //set back button
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener{
            finish()
        }

        //calling the addDateToDatabase() to add the date and time in database
        addDateToDatabase()

    }

    //method for adding date to database and formatting the date
    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("DATE:",""+dateTime)//making a log entry

        val sdf = SimpleDateFormat("dd MM yyyy HH:mm:ss", Locale.getDefault())//formatting date and time
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
        Log.i("DATE: ", "Added")
    }



}
