package io.github.kayquesanmartin.myimc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.kayquesanmartin.myimc.R
import io.github.kayquesanmartin.myimc.data.ImcRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImcAdapter(
    private val records: List<ImcRecord>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ImcAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imc, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.textView.text = "IMC: ${record.imcValue.format(2)} (${record.date.format()})"
        holder.deleteButton.setOnClickListener { onDelete(record.id) }
    }

    override fun getItemCount() = records.size
}

// Extensions para formatação
fun Double.format(digits: Int) = "%.${digits}f".format(this)
fun Date.format() = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)