package com.example.walkthroughcalories

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var weightEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var intensitySpinner: Spinner
    private lateinit var resultTextView: TextView

    private val intensityOptions = arrayOf("Light", "Moderate", "Hard")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weightEditText = findViewById(R.id.weightEditText)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)
        intensitySpinner = findViewById(R.id.intensitySpinner)
        resultTextView = findViewById(R.id.resultTextView)

        // Set up the Spinner with options
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, intensityOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        intensitySpinner.adapter = adapter

        val calculateButton: Button = findViewById(R.id.calculateButton)
        calculateButton.setOnClickListener { calculateCalories() }
    }

    private fun calculateCalories() {
        val weight = weightEditText.text.toString().toIntOrNull() ?: return
        val gender = when (genderRadioGroup.checkedRadioButtonId) {
            R.id.maleRadioButton -> "male"
            R.id.femaleRadioButton -> "female"
            else -> ""
        }
        val intensity = intensitySpinner.selectedItem.toString()

        val baseCalories = if (gender == "male") 2500 else 2000
        val intensityMultiplier = when (intensity) {
            "Light" -> 1.2
            "Moderate" -> 1.5
            "Hard" -> 1.8
            else -> 1.0
        }

        val calories = (baseCalories * intensityMultiplier * (weight / 80.0)).toInt()
        resultTextView.text = getString(R.string.calories_result, calories)
    }
}
