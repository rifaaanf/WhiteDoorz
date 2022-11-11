package com.example.whitedoorz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whitedoorz.databinding.ActivityDisplayRuanganBinding
import com.example.whitedoorz.room.Constant
import com.example.whitedoorz.room.Ruangan
import com.example.whitedoorz.room.RuanganDB
import kotlinx.android.synthetic.main.activity_display_gedung.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayRuanganActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDisplayRuanganBinding
    lateinit var ruanganAdapter: RuanganAdapter

    val db by lazy { RuanganDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayRuanganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()

        // Action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Data Ruangan"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newdata -> {
                binding.buttonCreate.performClick()

                }

            R.id.exit -> {
//               close app
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Exit")
                builder.setMessage("Are you sure you want to exit?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    finishAffinity()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
            intentEdit(0, Constant.TYPE_CREATE)
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
                intentEdit(ruangan.ruangan_id, Constant.TYPE_READ)
            }

            override fun onUpdate(ruangan: Ruangan) {
                intentEdit(ruangan.ruangan_id, Constant.TYPE_UPDATE)
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
            setTitle("Delete")
            setMessage("Are you sure want to delete ${ruangan.ruangan}?" )
            setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Yes") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.ruanganDao().deleteRuangan(ruangan)
                    loadRuangan()
                }
            }
        }
        alertDialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}