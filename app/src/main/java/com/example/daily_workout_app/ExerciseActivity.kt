package com.example.daily_workout_app

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.custom_dialog_for_exit.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * There are two timers used
 * 1) RestTimer: This timer is for the user to get ready upcoming exercise
 * 2) ExercieTimer: This timer is for the user while performing the exercise
 *
 * ExerciseActivity is extended with TextToSpeech OnInitListener
 */


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //variable for rest timer
    private var restTimer: CountDownTimer?=null
    private var restProgress = 0// progress counts from 0 to 10

    //TO DO: change the restTimerDuration from 2 to 10 seconds after testing
    private var restTimerDuration: Long = 2

    //variable for exercise timer
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress = 0// progress from 0 to 30


    //TO DO: change the exerciseTimerDuration from 2 to 30 seconds after testing
    private var exerciseTimerDuration : Long = 2
    private var exerciseList: ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1

    //text to speech variable
    private var tts: TextToSpeech? = null

    //adding media player
    private var player: MediaPlayer? = null

    //creating exercseAdapter for recyclerView
    private var exerciseAdapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        //on clicking the back button a dialog box will pop up
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        //adding text to speech feature
        tts = TextToSpeech(this,this)

        exerciseList = Constants.defaultExerciseList()
        setupRestView()

        setupExerciseStatusRecyclerView()
    }

    //onDestroy() is for resetting the timers and stop and shutdown the tts
    override fun onDestroy(){
        //resetting the restTimer
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        //resetting the exerciseTimer
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

       //destroying text to speech feature variable tts
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
      //destroying media player
        if(player != null){
            player!!.stop()
        }
        //resetting the timer
        super.onDestroy()
    }

//This fun is for setting the rest timer for the user to get ready for upcoming exercise
    private fun setRestProgressBar(){
        progressBar.progress = restProgress
        restTimer = object: CountDownTimer(restTimerDuration*1000, 1000){
            //onTick() is for countdown interval
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = restTimerDuration.toInt() - restProgress
                tvTimer.text = (restTimerDuration.toInt() - restProgress).toString()
            }

             //on finishing of the restTimer progressing the exercise timer
            override fun onFinish() {
                currentExercisePosition++

                //setting the current exercise to true
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                //once the restTimer is over move to next Exercise screen
                setupExerciseView()
            }
        }.start()
    }

//  This function is for starting ExerciseTimer after the user is ready to do exercise
    private fun setExerciseProgressBar(){
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration*1000, 1000){
            //onTick() is for the ExerciseTimer progress
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBar.progress = exerciseTimerDuration.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            //on finishing a current exercise either move to next or to finish screen
            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    //if all exercise performance is not completed
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    //  Call this when exercises activity is done and should be closed.
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    //setting up the Rest Screen for user to get ready
    private fun setupRestView(){
        try{
            //added the media player song
            player = MediaPlayer.create(applicationContext,R.raw.background)
            //preventing the media looping
            player!!.isLooping = false //this will only play the song once
            player!!.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

        //making the exercise screen invisible nad rest screen visible
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE


        if(restTimer != null){
            //resetting the rest timer
            restTimer!!.cancel()
            restProgress = 0
        }

        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }

    //setting the exercise screen
    private fun setupExerciseView(){

        //visibility use
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE


        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        //Function is used to speak the text ie current exercise name
        speakOut(exerciseList!![currentExercisePosition].getName())


        setExerciseProgressBar()

        //setting the exercise images and exercise name
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

//   This is TextToSpeech override function
//    Called to signal the completion of the TextToSpeech engine initialization
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


    //Function is used to speak the text ie current exercise name
    private fun speakOut(text: String){
        //ignore this error: It working fine after installing the app
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(this, exerciseList!!)
        rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.custom_dialog_for_exit)
        customDialog.tvYes.setOnClickListener{
            finish()
            customDialog.dismiss()
        }

        customDialog.tvNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }
}
