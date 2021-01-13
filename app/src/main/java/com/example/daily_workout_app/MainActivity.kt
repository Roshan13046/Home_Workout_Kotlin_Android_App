package com.example.daily_workout_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //on clicking the Start button
        llStart.setOnClickListener {
            //Intent is used to switch to new actvity screen
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        //on clicking the BMI calculator button
        llBMI.setOnClickListener{
            val intent = Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        //on clicking History button
        llHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
