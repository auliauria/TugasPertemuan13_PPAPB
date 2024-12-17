package com.example.tugaspertemuan13

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugaspertemuan13.databinding.ActivityEditBinding
import com.example.tugaspertemuan13.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data Pemilih yang dikirim
        val pemilih = intent.getSerializableExtra("pemilih") as? Pemilih

        // Log untuk memeriksa apakah Pemilih berhasil dikirim
        Log.d("EditActivity", "Received Pemilih: $pemilih")

        // Isi form dengan data lama
        pemilih?.let {
            binding.etName.setText(it.nama)
            binding.etNIK.setText(it.nik)
            binding.etAddress.setText(it.address)
            if (it.gender == "Laki-laki") {
                binding.rbMale.isChecked = true
            } else {
                binding.rbFemale.isChecked = true
            }
        } ?: run {
            finish()
        }

        // Simpan data yang diperbarui
        binding.btnSave.setOnClickListener {
            if (validateInput()) {
                val updatedPemilih = Pemilih(
                    nik = binding.etNIK.text.toString(),
                    nama = binding.etName.text.toString(),
                    gender = if (binding.rbMale.isChecked) "Laki-laki" else "Perempuan",
                    address = binding.etAddress.text.toString()
                )

                // Kirim hasil update kembali ke HomeActivity
                val resultIntent = Intent().apply {
                    putExtra("updated_pemilih", updatedPemilih)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                // Informasi error atau validasi tidak lengkap
                Log.e("EditActivity", "Validation failed")
                // Bisa tambahkan toast atau dialog untuk menunjukkan validasi error
            }
        }
    }

    private fun validateInput(): Boolean {
        val name = binding.etName.text.toString()
        val nik = binding.etNIK.text.toString()
        val address = binding.etAddress.text.toString()
        val genderChecked = binding.rbMale.isChecked || binding.rbFemale.isChecked

        return when {
            name.isBlank() -> {
                binding.etName.error = "Nama tidak boleh kosong"
                false
            }
            nik.isBlank() -> {
                binding.etNIK.error = "NIK tidak boleh kosong"
                false
            }
            address.isBlank() -> {
                binding.etAddress.error = "Alamat tidak boleh kosong"
                false
            }
            !genderChecked -> {
                Log.e("EditActivity", "Gender harus dipilih")
                false
            }
            else -> true
        }
    }
}