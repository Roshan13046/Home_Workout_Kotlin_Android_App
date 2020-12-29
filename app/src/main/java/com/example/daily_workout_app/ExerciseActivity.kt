package com.example.daily_workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {
    //variable for rest timer
    private var restTimer: CountDownTimer?=null
    private var restProgress = 0// progress from 0 to 10
    private var restTimerDuration: Long = 10

    //variable for exercise timer
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress = 0// progress from 0 to 30
    private var exerciseTimerDuration : Long = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }

    override fun onDestroy(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        super.onDestroy()
    }

    private fun setRestProgressBar(){
        progressBar.progress = restProgress
        restTimer = object: CountDownTimer(restTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = restTimerDuration.toInt() - restProgress
                tvTimer.text = (restTimerDuration.toInt() - restProgress).toString()
            }

            override fun onFinish() {
                //exercise view called here
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBar.progress = exerciseTimerDuration.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,
                    "Now, We will Start the next rest screen.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    private fun setupRestView(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setupExerciseView(){

        //visibility use
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE


        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

}
