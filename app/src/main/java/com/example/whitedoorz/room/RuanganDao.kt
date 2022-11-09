package com.example.whitedoorz.room

import androidx.room.*

@Dao
interface RuanganDao {
    @Insert
    suspend fun insertRuangan(ruangan: Ruangan)

    @Update
    suspend fun updateRuangan(ruangan: Ruangan)

    @Delete
    suspend fun deleteRuangan(ruangan: Ruangan)

    @Query("SELECT * FROM ruangan")
    suspend fun getAllRuangan(): List<Ruangan>

    @Query("SELECT * FROM ruangan WHERE id =:ruangan_id")
    suspend fun getRuangan(ruangan_id: Int): List<Ruangan>
}