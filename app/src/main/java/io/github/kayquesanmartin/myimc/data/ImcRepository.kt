package io.github.kayquesanmartin.myimc.data

import kotlinx.coroutines.flow.Flow

class ImcRepository(private val imcDao: ImcDao) {
    val allRecords: Flow<List<ImcRecord>> = imcDao.getAllRecords()

    suspend fun insert(record: ImcRecord) {
        imcDao.insert(record)
    }

    suspend fun delete(id: Int) {
        imcDao.delete(id)
    }
}