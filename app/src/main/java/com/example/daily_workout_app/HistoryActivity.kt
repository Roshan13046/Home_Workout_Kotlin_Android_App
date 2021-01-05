package com.example.daily_workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_bmi_activity)

        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "History"
        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
