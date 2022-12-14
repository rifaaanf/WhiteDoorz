package com.example.whitedoorz.gedung

import androidx.room.*

@Dao
interface GedungDao {
    @Insert
    suspend fun insertGedung(gedung: Gedung)

    @Update
    suspend fun updateGedung(gedung: Gedung)

    @Delete
    suspend fun deleteGedung(gedung: Gedung)

    @Query("SELECT * FROM gedung")
    suspend fun getAllGedung(): List<Gedung>

    @Query("SELECT * FROM gedung WHERE gedung_id =:gedung_id")
    suspend fun getGedung(gedung_id: Int): List<Gedung>
}