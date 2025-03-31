package io.github.kayquesanmartin.myimc

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var mainButton: Button
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var unitSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        mainButton = findViewById(R.id.mainButton)
        heightEditText = findViewById(R.id.heightEditText)
        weightEditText = findViewById(R.id.weightEditText)
        unitSpinner = findViewById(R.id.spinner)

        mainButton.setOnClickListener {
            if (validateInput()) {
                val height: Double = getConvertedHeight()
                val weight: Double = weightEditText.text.toString().toDouble()

                Toast.makeText(
                    this,
                    "IMC calculado com sucesso!",
                    Toast.LENGTH_LONG
                ).show()

                val imc = weight / (height * height)

                resultTextView.text = when {
                    imc < 18.5 -> getString(R.string.underweight)
                    imc in 18.5..24.9 -> getString(R.string.normal)
                    imc in 25.0..29.9 -> getString(R.string.overweight)
                    imc in 30.0..34.9 -> getString(R.string.obesityI)
                    imc in 35.0..39.9 -> getString(R.string.obesityII)
                    else -> getString(R.string.obesityIII)
                }
            }
        }
    }

    private fun validateInput(): Boolean {

        val height = heightEditText.text.toString()
        val weight = weightEditText.text.toString()

        if (TextUtils.isEmpty(height)) {
            heightEditText.error = "Altura é obrigatória"
            heightEditText.requestFocus()
            return false
        }

        if (height.toDoubleOrNull() == null) {
            heightEditText.error = "Altura inválida"
            heightEditText.requestFocus()
            return false
        }

        if (TextUtils.isEmpty(weight)) {
            weightEditText.error = "Peso é obrigatório"
            weightEditText.requestFocus()
            return false
        }

        if (weight.toDoubleOrNull() == null) {
            weightEditText.error = "Peso inválido"
            weightEditText.requestFocus()
            return false
        }

        return true

    }

    private fun getConvertedHeight(): Double {
        val height = heightEditText.text.toString().toDouble()
        val selectedUnit = unitSpinner.selectedItem.toString()

        return if (selectedUnit == "ft") {
            height * 0.3048
        } else {
            height
        }
    }
}
