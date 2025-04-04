package io.github.kayquesanmartin.myimc

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    private lateinit var mainButton: Button
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var heightSpinner: Spinner
    private lateinit var weightSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        mainButton = findViewById(R.id.mainButton)
        heightEditText = findViewById(R.id.heightEditText)
        weightEditText = findViewById(R.id.weightEditText)
        heightSpinner = findViewById(R.id.heightSpinner)
        weightSpinner = findViewById(R.id.weightSpinner)

        heightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                formatHeight()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        heightEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                heightEditText.removeTextChangedListener(this)
                formatHeight()
                heightEditText.addTextChangedListener(this)
            }
        })

        weightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                formatWeight()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        weightEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                weightEditText.removeTextChangedListener(this)
                formatWeight()
                weightEditText.addTextChangedListener(this)
            }
        })

        mainButton.setOnClickListener {
            if (validateInput()) {
                val height: Double = convertHeightToMeters()
                val weight: Double = convertWeightToKilograms()

                val imc = weight / (height * height)

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("IMC_VALUE", imc)
                startActivity(intent)
            }
        }
    }

    private fun formatWeight() {
        val unit = weightSpinner.selectedItem.toString()
        val input = weightEditText.text.toString()

        if (input.isNotEmpty()) {
            when (unit) {
                "kg" -> {
                    if (input.length >= 3 && input.toIntOrNull() != null) {
                        val kilograms = input.substring(0, input.length - 1)
                        val grams = input.substring(input.length - 1)
                        weightEditText.setText("$kilograms.$grams")
                        weightEditText.setSelection(weightEditText.text.length)
                    }
                }

                "lb" -> {
                    val pounds = input.toIntOrNull() ?: 0
                    weightEditText.setText("$pounds")
                    weightEditText.setSelection(weightEditText.text.length)
                }
            }
        }
    }

    private fun convertWeightToKilograms(): Double {
        val unit = weightSpinner.selectedItem.toString()
        val input = weightEditText.text.toString()

        return when (unit) {
            "kg" -> input.toDouble()
            "lb" -> (input.toDouble() * 0.45359237)
            else -> 0.0
        }
    }

    private fun formatHeight() {
        val unit = heightSpinner.selectedItem.toString()
        val input = heightEditText.text.toString()

        if (input.isNotEmpty()) {
            when (unit) {
                "m" -> {
                    if (input.length >= 3 && input.toIntOrNull() != null) {
                        val meters = input.substring(0, input.length - 2)
                        val centimeters = input.substring(input.length - 2)
                        heightEditText.setText("$meters.$centimeters")
                        heightEditText.setSelection(heightEditText.text.length)
                    }
                }
                "ft" -> {
                    val parts = input.split("'")
                    if (parts.size == 2) {
                        val feet = parts[0].toIntOrNull() ?: 0
                        val inches = parts[1].replace("\"", "").toIntOrNull() ?: 0
                        heightEditText.setText("$feet'$inches\"")
                        heightEditText.setSelection(heightEditText.text.length)
                    }
                }
            }
        }
    }

    private fun convertHeightToMeters(): Double {
        val unit = heightSpinner.selectedItem.toString()
        val input = heightEditText.text.toString()

        return when (unit) {
            "m" -> input.toDoubleOrNull() ?: 0.0
            "ft" -> {
                val parts = input.split("'")
                if (parts.size == 2) {
                    val feet = parts[0].toIntOrNull() ?: 0
                    val inches = parts[1].replace("\"", "").toIntOrNull() ?: 0
                    (feet * 0.3048) + (inches * 0.0254)
                } else {
                    0.0
                }
            }
            else -> 0.0
        }
    }

    private fun validateInput(): Boolean {
        if (heightEditText.text.isEmpty() || weightEditText.text.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return false
        }
        if (heightEditText.text.toString().toDoubleOrNull() == 0.0 || weightEditText.text.toString().toDoubleOrNull() == 0.0) {
            Toast.makeText(this, "Valores inválidos, insira um valor maior que zero.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

