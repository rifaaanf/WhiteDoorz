package com.example.whitedoorz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val btn_ruangan = findViewById<Button>(R.id.ruangan)
        btn_ruangan.setOnClickListener {
            val intent = Intent(this, DisplayRuanganActivity::class.java)
            startActivity(intent)
        }

        val btn_gedung = findViewById<Button>(R.id.gedung)
        btn_gedung.setOnClickListener {
            val intent = Intent(this, DisplayGedungActivity::class.java)
            startActivity(intent)
        }
    }
}