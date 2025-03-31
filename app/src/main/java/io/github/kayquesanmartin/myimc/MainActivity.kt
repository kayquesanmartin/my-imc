package io.github.kayquesanmartin.myimc

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var mainButton: Button
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText

    fun validateInput(): Boolean {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        mainButton = findViewById(R.id.mainButton)
        heightEditText = findViewById(R.id.heightEditText)
        weightEditText = findViewById(R.id.weightEditText)

        mainButton.setOnClickListener {
            if (validateInput()) {
                val height: Double = heightEditText.text.toString().toDouble()
                val weight: Double = weightEditText.text.toString().toDouble()

                val imc = weight / (height * height)

                when {
                    imc < 18.5 -> resultTextView.text = getString(R.string.underweight)
                    imc in 18.5..24.9 -> resultTextView.text = getString(R.string.normal)
                    imc in 25.0..29.9 -> resultTextView.text = getString(R.string.overweight)
                    imc in 30.0..34.9 -> resultTextView.text = getString(R.string.obesityI)
                    imc in 35.0..39.9 -> resultTextView.text = getString(R.string.obesityII)
                    else -> resultTextView.text = getString(R.string.obesityIII)
                }
            }
        }
    }
}