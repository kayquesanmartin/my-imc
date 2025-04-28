package io.github.kayquesanmartin.myimc.ui

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

        // Coletar os dados do Flow (solução moderna com Coroutines)
        lifecycleScope.launch {
            imcRepository.allRecords.collect { records ->
                recyclerView.adapter = ImcAdapter(records) { id ->
                    lifecycleScope.launch {
                        imcRepository.delete(id)
                    }
                }
            }
        }
    }
}