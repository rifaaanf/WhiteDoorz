package com.example.whitedoorz.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Gedung(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val gedung: String,
    val lantai: Int,
)
