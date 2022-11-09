package com.example.whitedoorz.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ruangan(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ruangan: String,
    val kapasitas: Int,
)
