package com.example.whitedoorz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whitedoorz.databinding.ActivityDisplayGedungBinding
import com.example.whitedoorz.gedung.Constant
import com.example.whitedoorz.gedung.Gedung
import com.example.whitedoorz.gedung.GedungDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayGedungActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDisplayGedungBinding
    lateinit var gedungAdapter: GedungAdapter

    val db by lazy { GedungDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayGedungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        setupRecyclerView()

        // Action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Data Gedung"
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
        loadGedung()
    }

    fun loadGedung(){
        CoroutineScope(Dispatchers.IO).launch {
            val gedungs = db.gedungDao().getAllGedung()
            withContext(Dispatchers.Main){
                gedungAdapter.setData(gedungs)
            }
        }
    }

    private fun setupListener() {
        binding.buttonCreate.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(gedungId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditGedungActivity::class.java)
                .putExtra("intent_id", gedungId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        gedungAdapter = GedungAdapter(arrayListOf(), object : GedungAdapter.OnAdapterListener{
            override fun onRead(gedung: Gedung) {
                intentEdit(gedung.gedung_id, Constant.TYPE_READ)
            }

            override fun onUpdate(gedung: Gedung) {
                intentEdit(gedung.gedung_id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(gedung: Gedung) {
                deleteDialog(gedung)
            }

        })
        binding.listGedung.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = gedungAdapter
        }
    }

    private fun deleteDialog(gedung: Gedung) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Delete")
            setMessage("Are you sure want to delete ${gedung.gedung}?" )
            setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Yes") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.gedungDao().deleteGedung(gedung)
                    loadGedung()
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