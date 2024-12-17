package com.example.tugaspertemuan13

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.tugaspertemuan13.database.PemilihDao
import com.example.tugaspertemuan13.database.PemilihRoomDatabase
import com.example.tugaspertemuan13.databinding.ActivityEntryBinding
import com.example.tugaspertemuan13.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntryBinding
    private lateinit var pemilihDao: PemilihDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = PemilihRoomDatabase.getDatabase(this)
        pemilihDao = db?.PemilihDao() ?: throw IllegalStateException("PemilihDao cannot be null")

        val nameField = binding.etName
        val nikField = binding.etNIK
        val genderGroup = binding.rgGender
        val addressField = binding.etAddress
        val saveButton = binding.btnSave

        saveButton.setOnClickListener {
            val name = nameField.text.toString()
            val nik = nikField.text.toString()
            val gender = if (genderGroup.checkedRadioButtonId == R.id.rbMale) "Laki-laki" else "Perempuan"
            val address = addressField.text.toString()

            val pemilih = Pemilih(nik, name, gender, address)
            // Simpan data dengan Coroutine
            lifecycleScope.launch(Dispatchers.IO) {
                pemilihDao.insert(pemilih)  // Menyimpan data di latar belakang
            }
            finish()
        }
    }
}