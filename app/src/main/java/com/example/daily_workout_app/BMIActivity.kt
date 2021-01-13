package com.example.daily_workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmi_activity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        //on clicking the calculate BMI button below fun will find the BMI
        btnCalculateUnits.setOnClickListener{

            //checking current unit system
            if(currentVisibleView.equals(METRIC_UNITS_VIEW)){
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
            }else{
                if(validateUsUnits()){
                    val usUnitHeightValueFeet: String = etUsUnitHeightFeet.text.toString()//Height in Feet
                    val usUnitHeightValueInch: String= etUsUnitHeightInch.text.toString()//Height in inch
                    val usUnitWeightValue: Float = etUsUnitWeight.text.toString().toFloat()//Weight in pound

                    //1 feet equals to 12 inch so converting feet height into inches to calculate the BMI
                    val USheightValue = usUnitHeightValueInch.toFloat() + (usUnitHeightValueFeet.toFloat() * 12)

                    //formula to calculate BMI Value in American System
                    val bmiValue  = 703 * (usUnitWeightValue / (USheightValue*USheightValue))


                    //displaying the BMI value
                    displayBMIResult(bmiValue)
                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values." , Toast.LENGTH_SHORT).show()
                }
            }


        }

        //making units system visible
        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener{ group, checkedId ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }
    }

    //Method to make the Metric Units Visible
    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        //clearing the text and making the text empty
        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()


        tilUSUnitWeight.visibility = View.GONE
        llUsUnitsHeight.visibility = View.GONE

        llDisplayBMIResult.visibility = View.INVISIBLE
    }

    //Method to make the US Units Visible
    private fun makeVisibleUSUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        //clearing the text and making the text empty
        etUsUnitWeight.text!!.clear()
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()


        tilUSUnitWeight.visibility = View.VISIBLE
        llUsUnitsHeight.visibility = View.VISIBLE

        llDisplayBMIResult.visibility = View.INVISIBLE
    }


    private fun displayBMIResult(bmi: Float){
        val bmiLabel : String
        val bmiDescription : String

        //Messages shown to user according to BMI values.
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
        llDisplayBMIResult.visibility = View.VISIBLE


//        tvYourBMI.visibility = View.VISIBLE
//        tvBMIValue.visibility = View.VISIBLE
//        tvBMIType.visibility = View.VISIBLE
//        tvBMIDescription.visibility = View.VISIBLE

        //Rounding off the bmi value and converting bmi value to string
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        //setting the text values
        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

    //validating Metric Units inputs
    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(etMetricUnitWeight.text.toString().isEmpty() || etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    //validating US Units inputs
    private fun validateUsUnits(): Boolean{
        var isValid = true
        if(etUsUnitHeightFeet.text.toString().isEmpty() || etUsUnitWeight.text.toString().isEmpty() || etUsUnitHeightInch.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }
}
