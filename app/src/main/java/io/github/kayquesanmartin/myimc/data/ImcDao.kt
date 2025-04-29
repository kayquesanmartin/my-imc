package io.github.kayquesanmartin.myimc.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ImcDao {
    @Insert
    suspend fun insert(record: ImcRecord)

    @Update
    suspend fun update(record: ImcRecord)

    @Query("SELECT * FROM imc_records ORDER BY date DESC")
    fun getAllRecords(): Flow<List<ImcRecord>>

    @Query("DELETE FROM imc_records WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM imc_records WHERE id = :id")
    suspend fun getRecordById(id: Int): ImcRecord
}