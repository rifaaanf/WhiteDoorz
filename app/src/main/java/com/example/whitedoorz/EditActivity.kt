package com.example.whitedoorz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.whitedoorz.databinding.ActivityEditBinding
import com.example.whitedoorz.gedung.GedungDB
import com.example.whitedoorz.room.Constant
import com.example.whitedoorz.room.Ruangan
import com.example.whitedoorz.room.RuanganDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditActivity : AppCompatActivity() {

    val db by lazy { RuanganDB(this)}
    val gedungdb by lazy { GedungDB(this) }

    private var ruanganId: Int = 0

    private lateinit var binding : ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                binding.buttonUpdate.visibility = View.GONE
                getAllGedung()
            }
            Constant.TYPE_READ -> {
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.GONE
                getRuangan()
            }
            Constant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getRuangan()
            }
        }
    }

    private fun setupListener() {
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ruanganDao().insertRuangan(
                    Ruangan(0, binding.editTitle.text.toString(), binding.editKapasitas.text.toString().toInt(), binding.spinnerGedung.selectedItem.toString().toInt())
                )
                finish()
            }
        }

        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ruanganDao().updateRuangan(
                    Ruangan(ruanganId, binding.editTitle.text.toString(), binding.editKapasitas.text.toString().toInt(), binding.spinnerGedung.selectedItem.toString().toInt())
                )
                finish()
            }
        }
    }

    fun getRuangan(){
        ruanganId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val ruangans = db.ruanganDao().getRuangan(ruanganId)[0]
            binding.editTitle.setText(ruangans.ruangan)
            binding.editKapasitas.setText(ruangans.kapasitas.toString())
        }
    }

    fun getAllGedung() {
        CoroutineScope(Dispatchers.IO).launch {
            val gedungs = gedungdb.gedungDao().getAllGedung()
//            Get only the gedung name and gedung_id for value
            val gedungName = gedungs.map { it.gedung }
            val gedungId = gedungs.map { it.gedung_id }
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    this@EditActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    gedungId
                )
                binding.spinnerGedung.adapter = adapter
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}



