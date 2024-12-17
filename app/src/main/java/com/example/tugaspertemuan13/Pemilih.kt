package com.example.tugaspertemuan13

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "data_pemilih")
data class Pemilih(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    val nik: String,
    @ColumnInfo(name = "nama")
    val nama: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "alamat")
    val address: String
) : Serializable
