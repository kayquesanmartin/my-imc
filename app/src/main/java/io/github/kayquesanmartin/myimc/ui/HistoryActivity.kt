package io.github.kayquesanmartin.myimc.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.kayquesanmartin.myimc.R
import io.github.kayquesanmartin.myimc.adapter.ImcAdapter
import io.github.kayquesanmartin.myimc.data.AppDatabase
import io.github.kayquesanmartin.myimc.data.ImcRepository
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var imcRepository: ImcRepository
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = AppDatabase.getDatabase(applicationContext)
        imcRepository = ImcRepository(db.imcDao())

        lifecycleScope.launch {
            imcRepository.allRecords.collect { records ->
                recyclerView.adapter = ImcAdapter(records,
                    onDelete = { id ->
                        lifecycleScope.launch {
                            imcRepository.delete(id)
                        }
                    },
                    onEdit = { record ->
                        val intent = Intent(this@HistoryActivity, EditImcActivity::class.java)
                        intent.putExtra("RECORD", record)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}