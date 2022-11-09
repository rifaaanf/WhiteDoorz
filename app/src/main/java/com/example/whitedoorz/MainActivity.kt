package com.example.whitedoorz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whitedoorz.databinding.ActivityMainBinding
import com.example.whitedoorz.room.Constant
import com.example.whitedoorz.room.Ruangan
import com.example.whitedoorz.room.RuanganDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var ruanganAdapter: RuanganAdapter

    val db by lazy { RuanganDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadRuangan()
    }

    fun loadRuangan(){
        CoroutineScope(Dispatchers.IO).launch {
            val ruangans = db.ruanganDao().getAllRuangan()
            withContext(Dispatchers.Main){
                ruanganAdapter.setData(ruangans)
            }
        }
    }

    private fun setupListener() {
        binding.buttonCreate.setOnClickListener {
            //startActivity(Intent(this, EditActivity::class.java))
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(ruanganId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", ruanganId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        ruanganAdapter = RuanganAdapter(arrayListOf(), object : RuanganAdapter.OnAdapterListener{
            override fun onRead(ruangan: Ruangan) {
                intentEdit(ruangan.id, Constant.TYPE_READ)
            }

            override fun onUpdate(ruangan: Ruangan) {
                intentEdit(ruangan.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(ruangan: Ruangan) {
                deleteDialog(ruangan)
            }

        })
        binding.listRuangan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = ruanganAdapter
        }
    }

    private fun deleteDialog(ruangan: Ruangan) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${ruangan.ruangan}?" )
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.ruanganDao().deleteRuangan(ruangan)
                    loadRuangan()
                }
            }
        }
        alertDialog.show()
    }
}