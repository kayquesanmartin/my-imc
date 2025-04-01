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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainButton: Button
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var unitSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mainButton = findViewById(R.id.mainButton)
        heightEditText = findViewById(R.id.heightEditText)
        weightEditText = findViewById(R.id.weightEditText)
        unitSpinner = findViewById(R.id.spinner)

        unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent : AdapterView<*>?, view : View?, position : Int, id : Long) {
                formatHeight()
            }

            override fun onNothingSelected(parent : AdapterView<*>?) {}
        }

        heightEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int, after : Int) {}

            override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {}

            override fun afterTextChanged(s : Editable?) {
                heightEditText.removeTextChangedListener(this)
                formatHeight()
                heightEditText.addTextChangedListener(this)
            }
        })

        mainButton.setOnClickListener {
            if (validateInput()) {
                val height: Double = convertHeightToMeters()
                val weight: Double = weightEditText.text.toString().toDouble()

                val imc = weight / (height * height)

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("IMC_VALUE", imc)
                startActivity(intent)
            }
        }
    }

    private fun formatHeight() {
        val unit = unitSpinner.selectedItem.toString()
        val input = heightEditText.text.toString()

        if (input.isNotEmpty() && input.toIntOrNull() != null) {
            when (unit) {
                "m" -> {
                    if (input.length >= 3) {
                        val meters = input.substring(0, input.length - 2)
                        val centimeters = input.substring(input.length - 2)
                        heightEditText.setText("$meters.$centimeters")
                        heightEditText.setSelection(heightEditText.text.length)
                    }
                }
                "ft" -> {
                    val inches = (input.toInt() * 0.393701).toInt()
                    val feet = inches / 12
                    val remainingInches = inches % 12
                    heightEditText.setText("$feet'$remainingInches\"")
                    heightEditText.setSelection(heightEditText.text.length)
                }
            }
        }
    }

    private fun convertHeightToMeters() : Double {
        val unit = unitSpinner.selectedItem.toString()
        val input = heightEditText.text.toString()

        return when (unit) {
            "m" -> input.toDouble()
            "ft" -> {
                val parts = input.split("'")
                val feet = parts[0].toInt()
                val inches = parts[1].replace("\"", "").toInt()
                (feet * 0.3048) + (inches * 0.0254)
            }
            else -> 0.0
        }
    }

    private fun validateInput(): Boolean {

        if (heightEditText.text.isEmpty() || weightEditText.text.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
