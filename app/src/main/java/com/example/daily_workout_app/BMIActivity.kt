package com.example.daily_workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmi_activity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDefaultDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        //on clicking the calculate BMI button below fun will find the BMI
        btnCalculateUnits.setOnClickListener{
            if(validateMetricUnits()){
                //converting height from centimeters to meters
                val heightValue : Float = etMetricUnitHeight.text.toString().toFloat() / 100
                //weight is in kg
                val weightValue : Float = etMetricUnitWeight.text.toString().toFloat()

                val bmiValue = weightValue / (heightValue * heightValue)
                //calling the fun and passing the bmiValue as a parameter
                displayBMIResult(bmiValue)

            }else{
                Toast.makeText(this@BMIActivity, "Please enter valid values." , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel : String
        val bmiDescription : String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely Underweight"
            bmiDescription = "You need to take more care of your health! Eat more!"
        }else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severly underweight"
            bmiDescription = "You need to take care of your health! Eat more!"
        }else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f)<= 0){
            bmiLabel = "Underweight"
            bmiDescription = "You need to take care of your health! Eat more!"
        }else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good fit shape!"
        }else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! need to take care of yourself! Workout more"
        }else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your health! Workout daily"
        }else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <=0 ){
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "Oops! Your health is in a very dangerous condition! Act now!"
        }else{
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //making text contents visible
        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE

        //Rounding off the bmi value and converting bmi value to string
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        //setting the text values
        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(etMetricUnitWeight.text.toString().isEmpty() || etMetricUnitHeight.text.toString().isEmpty())
            isValid = false

        return isValid
    }
}
