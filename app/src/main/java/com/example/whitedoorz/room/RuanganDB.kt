package com.example.whitedoorz.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.whitedoorz.gedung.Gedung

@Database(entities = [Ruangan::class, Gedung::class], version = 3)


abstract class RuanganDB: RoomDatabase() {
    abstract fun ruanganDao(): RuanganDao

    companion object {
        @Volatile private var instance: RuanganDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RuanganDB::class.java,
            "ruangan.db"
        ).fallbackToDestructiveMigration().build()
    }
}