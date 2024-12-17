package com.example.tugaspertemuan13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspertemuan13.databinding.ItemPemilihBinding

class PemilihAdapter(
    private val pemilihList: List<Pemilih>,
    private val onEditClicked: (Pemilih) -> Unit,
    private val onDeleteClicked: (Pemilih) -> Unit,
    private val onDetailClicked: (Pemilih) -> Unit
) : RecyclerView.Adapter<PemilihAdapter.PemilihViewHolder>() {

    inner class PemilihViewHolder(val binding: ItemPemilihBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pemilih: Pemilih) {
            binding.tvName.text = pemilih.nama
            binding.btnEdit.setOnClickListener { onEditClicked(pemilih) }
            binding.btnDelete.setOnClickListener { onDeleteClicked(pemilih) }
            binding.btnDetails.setOnClickListener { onDetailClicked(pemilih) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemilihViewHolder {
        val binding = ItemPemilihBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PemilihViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PemilihViewHolder, position: Int) {
        holder.bind(pemilihList[position])
    }

    override fun getItemCount(): Int = pemilihList.size

}