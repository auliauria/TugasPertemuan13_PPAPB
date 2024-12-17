package com.example.tugaspertemuan13

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspertemuan13.database.PemilihDao
import com.example.tugaspertemuan13.database.PemilihRoomDatabase
import com.example.tugaspertemuan13.databinding.ActivityHomeBinding
import com.example.tugaspertemuan13.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PemilihAdapter
    private val pemilihList = mutableListOf<Pemilih>()
    private lateinit var pemilihDao: PemilihDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi database dan DAO
        val db = PemilihRoomDatabase.getDatabase(this)
        if (db != null) {
            pemilihDao = db.PemilihDao()!!
        }

        recyclerView = binding.recyclerViewData
        adapter = PemilihAdapter(pemilihList, ::onEditClicked, ::onDeleteClicked, ::onDetailClicked)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        binding.btnTambahData.setOnClickListener {
            startActivity(Intent(this, EntryActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup HomeActivity agar tidak kembali
        }

        // Penerimaan data dari EditActivity
        handleEditActivityResult()

        loadData()
    }

    private fun handleEditActivityResult() {
        if (intent.hasExtra("updated_pemilih")) {
            val updatedPemilih = intent.getSerializableExtra("updated_pemilih") as? Pemilih
            if (updatedPemilih != null) {
                val pemilihIndex = pemilihList.indexOfFirst { it.nik == updatedPemilih.nik }
                if (pemilihIndex != -1) {
                    pemilihList[pemilihIndex] = updatedPemilih
                    adapter.notifyItemChanged(pemilihIndex)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        pemilihDao.getAllPemilih().observe(this) { list ->
            pemilihList.clear()
            pemilihList.addAll(list)
            adapter.notifyDataSetChanged()
            Log.d("HomeActivity", "Data loaded successfully: $pemilihList")
        }
    }


    private fun onEditClicked(pemilih: Pemilih) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("pemilih", pemilih)
        startActivityForResult(intent, EDIT_REQUEST_CODE)
    }

    private fun onDeleteClicked(pemilih: Pemilih) {
        lifecycleScope.launch(Dispatchers.IO) {
            pemilihDao.delete(pemilih)
        }
    }

    private fun onDetailClicked(pemilih: Pemilih) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("pemilih", pemilih)
        startActivity(intent)
    }

    companion object {
        private const val EDIT_REQUEST_CODE = 1001 // Kode permintaan edit
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.getSerializableExtra("updated_pemilih")?.let { updatedPemilih ->
                val pemilihIndex = pemilihList.indexOfFirst { it.nik == (updatedPemilih as Pemilih).nik }
                if (pemilihIndex != -1) {
                    pemilihList[pemilihIndex] = updatedPemilih as Pemilih // Perbarui data
                    adapter.notifyItemChanged(pemilihIndex) // Notifikasi perubahan pada adapter
                }
            }
        }
    }
}