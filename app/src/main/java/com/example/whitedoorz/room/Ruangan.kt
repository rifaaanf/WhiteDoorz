package com.example.whitedoorz.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.whitedoorz.gedung.Gedung

@Entity(tableName = "ruangan",
foreignKeys = [
    ForeignKey(
        entity = Gedung::class,
        parentColumns = ["gedung_id"],
        childColumns = ["gedung_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Ruangan(
    @PrimaryKey(autoGenerate = true)
    val ruangan_id: Int,
    val ruangan: String,
    val kapasitas: Int,
    val gedung_id: Int
)
