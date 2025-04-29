package io.github.kayquesanmartin.myimc.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import io.github.kayquesanmartin.myimc.R
import io.github.kayquesanmartin.myimc.data.AppDatabase
import io.github.kayquesanmartin.myimc.data.ImcRecord
import kotlinx.coroutines.launch
import java.util.Date

class EditImcActivity : AppCompatActivity() {
    private lateinit var editHeight: EditText
    private lateinit var editWeight: EditText
    private lateinit var saveButton: Button
    private lateinit var imcRecord: ImcRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_imc)

        editHeight = findViewById(R.id.editHeight)
        editWeight = findViewById(R.id.editWeight)
        saveButton = findViewById(R.id.saveButton)

        imcRecord = intent.getSerializableExtra("RECORD") as ImcRecord

        editHeight.setText(imcRecord.height.toString())
        editWeight.setText(imcRecord.weight.toString())

        saveButton.setOnClickListener {
            val newHeight = editHeight.text.toString().toDouble()
            val newWeight = editWeight.text.toString().toDouble()
            val newImc = newWeight / (newHeight * newHeight)

            val updatedRecord = imcRecord.copy(
                height = newHeight,
                weight = newWeight,
                imcValue = newImc,
                date = Date()
            )

            lifecycleScope.launch {
                val recordId = intent.getIntExtra("RECORD_ID", 0)
                imcRecord = AppDatabase.getDatabase(this@EditImcActivity)
                    .imcDao()
                    .getRecordById(recordId)
                AppDatabase.getDatabase(applicationContext).imcDao().update(updatedRecord)
                finish()
            }
        }
    }
}