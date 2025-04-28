package io.github.kayquesanmartin.myimc.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "imc_records")
data class ImcRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val height: Double,
    val weight: Double,
    val imcValue: Double,
    val date: Date = Date()
)
