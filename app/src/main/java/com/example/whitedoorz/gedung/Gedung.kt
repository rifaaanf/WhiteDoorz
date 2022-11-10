package com.example.whitedoorz.gedung

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Gedung(
    @PrimaryKey(autoGenerate = true)
    val gedung_id: Int,
    val gedung: String,
    val lantai: Int,
)
