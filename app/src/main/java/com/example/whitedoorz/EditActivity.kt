package com.example.whitedoorz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.whitedoorz.databinding.ActivityEditBinding
import com.example.whitedoorz.room.Constant
import com.example.whitedoorz.room.Ruangan
import com.example.whitedoorz.room.RuanganDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    val db by lazy { RuanganDB(this)}
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
                    Ruangan(0, binding.editTitle.text.toString(), binding.editKapasitas.text.toString().toInt(), 1)
                )
                finish()
            }
        }

        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ruanganDao().updateRuangan(
                    Ruangan(ruanganId, binding.editTitle.text.toString(), binding.editKapasitas.text.toString().toInt(), 1)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}



