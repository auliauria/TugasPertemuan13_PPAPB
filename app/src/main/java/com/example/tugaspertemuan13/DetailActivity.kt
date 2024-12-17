package com.example.tugaspertemuan13

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugaspertemuan13.databinding.ActivityDetailBinding
import com.example.tugaspertemuan13.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data Pemilih dari intent
        val pemilih = intent.getSerializableExtra("pemilih") as? Pemilih
        pemilih?.let {
            // Menampilkan data Pemilih jika tidak null
            binding.detailName.text = "Nama: ${it.nama}"
            binding.detailNIK.text = "NIK: ${it.nik}"
            binding.detailGender.text = "Gender: ${it.gender}"
            binding.detailAlamat.text = "Alamat: ${it.address}"
        } ?: run {
            // Jika pemilih null, bisa beri pesan error atau finish activity
            binding.detailName.text = "Data tidak tersedia"
            binding.detailNIK.text = ""
            binding.detailGender.text = ""
            binding.detailAlamat.text = ""
            finish() // Optional: Tutup activity jika data pemilih hilang
        }

        // Tombol kembali untuk menutup activity
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}