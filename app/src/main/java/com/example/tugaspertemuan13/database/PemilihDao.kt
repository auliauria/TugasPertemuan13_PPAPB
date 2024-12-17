package com.example.tugaspertemuan13.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tugaspertemuan13.Pemilih

@Dao
interface PemilihDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pemilih: Pemilih)
    @Update
    fun update(pemilih: Pemilih)
    @Delete
    fun delete(pemilih: Pemilih)

    @Query("SELECT * from data_pemilih")
    fun getAllPemilih(): LiveData<List<Pemilih>>
}