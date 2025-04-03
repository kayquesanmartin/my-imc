package io.github.kayquesanmartin.myimc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var secondButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val resultTextView : TextView = findViewById(R.id.resultTextView)
        val messageTextView : TextView = findViewById(R.id.messageTextView)

        secondButton = findViewById(R.id.secondButton)

        val imc = intent.getDoubleExtra("IMC_VALUE", 0.0)

        messageTextView.text = "Seu IMC é: %.2f".format(imc)

        resultTextView.text = when {
            imc < 18.5 -> getString(R.string.underweight)
            imc in 18.5..24.9 -> getString(R.string.normal)
            imc in 25.0..29.9 -> getString(R.string.overweight)
            imc in 30.0..34.9 -> getString(R.string.obesityI)
            imc in 35.0..39.9 -> getString(R.string.obesityII)
            else -> getString(R.string.obesityIII)
        }

        secondButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

    }
}