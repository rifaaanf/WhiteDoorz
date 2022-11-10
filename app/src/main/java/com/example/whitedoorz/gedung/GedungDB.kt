package com.example.whitedoorz.gedung

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.whitedoorz.gedung.Gedung
import com.example.whitedoorz.gedung.GedungDao

@Database(entities = [Gedung::class], version = 1)
abstract class GedungDB: RoomDatabase() {
    abstract fun gedungDao(): GedungDao

    companion object {
        @Volatile private var instance: GedungDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GedungDB::class.java,
            "gedung.db"
        ).build()
    }
}