package com.example.whitedoorz.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ruangan(
    @PrimaryKey(autoGenerate = true)
    val ruangan_id: Int,
    val ruangan: String,
    val kapasitas: Int,
    val gedung: Int
)
