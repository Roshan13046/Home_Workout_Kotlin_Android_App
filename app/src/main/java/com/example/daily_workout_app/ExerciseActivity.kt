package com.example.daily_workout_app

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //variable for rest timer
    private var restTimer: CountDownTimer?=null
    private var restProgress = 0// progress from 0 to 10

    //TO DO: change the restTimerDuration from 2 to 10 seconds after testing
    private var restTimerDuration: Long = 5

    //variable for exercise timer
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress = 0// progress from 0 to 30


    //TO DO: change the exerciseTimerDuration from 4 to 30 seconds after testing
    private var exerciseTimerDuration : Long = 4
    private var exerciseList: ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    //text to speech variable
    private var tts: TextToSpeech? = null

    //adding media player
    private var player: MediaPlayer? = null



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

        tts = TextToSpeech(this,this)

        exerciseList = Constants.defaultExerciseList()
        setupRestView()
    }

    override fun onDestroy(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

//destroying tts
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
//destroying media player
        if(player != null){
            player!!.stop()
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
                currentExercisePosition++
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
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity,
                        "Hurray! You completed the 10 minutes workout.",
                        Toast.LENGTH_SHORT).show()
                }

//                Toast.makeText(this@ExerciseActivity,
//                    "Now, We will Start the next rest screen.",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }.start()
    }

    private fun setupRestView(){

        try{
            //added the media
            player = MediaPlayer.create(applicationContext,R.raw.background)

            //to prevent the media looping
            player!!.isLooping = false
            player!!.start()

        }catch (e: Exception){
            e.printStackTrace()
        }


        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
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

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            //setting the language to US English
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language specified is not supported")
                Toast.makeText(this,"Language not Supported",Toast.LENGTH_SHORT).show()
            }else{
                Log.e("TTS","Initialization Failed!")
            }
        }
    }

    private fun speakOut(text: String){
        //ignore this error: It working fine after installing the app
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

}
