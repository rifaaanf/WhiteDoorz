package com.example.whitedoorz.room

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

    @Query("SELECT * FROM gedung WHERE id =:gedung_id")
    suspend fun getGedung(gedung_id: Int): List<Gedung>
}