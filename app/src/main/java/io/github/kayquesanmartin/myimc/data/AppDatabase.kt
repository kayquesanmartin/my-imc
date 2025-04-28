package io.github.kayquesanmartin.myimc.data

import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.TypeConverters

@Database(entities = [ImcRecord::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imcDao(): ImcDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,  // Note o .class.java aqui
                    "imc_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}