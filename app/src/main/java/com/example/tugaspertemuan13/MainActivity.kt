package com.example.tugaspertemuan13

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugaspertemuan13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            // Ambil input username dan password (opsional, hanya untuk debug)
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            // Navigasi ke halaman Home (abaikan validasi)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Optional: Tutup MainActivity agar tidak bisa kembali dengan tombol back
        }
    }
}