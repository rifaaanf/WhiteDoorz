package com.example.whitedoorz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.whitedoorz.databinding.ActivityEditGedungBinding
import com.example.whitedoorz.gedung.Constant
import com.example.whitedoorz.gedung.Gedung
import com.example.whitedoorz.gedung.GedungDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditGedungActivity : AppCompatActivity() {

    val db by lazy { GedungDB(this)}
    private var gedungId: Int = 0

    private lateinit var binding : ActivityEditGedungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGedungBinding.inflate(layoutInflater)
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
                getGedung()
            }
            Constant.TYPE_UPDATE -> {
                binding.buttonSave.visibility = View.GONE
                getGedung()
            }
        }
    }

    private fun setupListener() {
        binding.buttonSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.gedungDao().insertGedung(
                    Gedung(0, binding.editTitle.text.toString(), binding.editLantai.text.toString().toInt())
                )
                finish()
            }
        }

        binding.buttonUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.gedungDao().updateGedung(
                    Gedung(gedungId, binding.editTitle.text.toString(), binding.editLantai.text.toString().toInt())
                )
                finish()
            }
        }
    }

    fun getGedung(){
        gedungId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val gedungs = db.gedungDao().getGedung(gedungId)[0]
            binding.editTitle.setText(gedungs.gedung)
            binding.editLantai.setText(gedungs.lantai.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}



