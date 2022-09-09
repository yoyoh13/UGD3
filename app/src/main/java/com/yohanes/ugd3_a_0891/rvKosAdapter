package com.yohanes.ugd3_a_0891

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yohanes.ugd3_a_0891.entity.Kos

class rvKosAdapter (private val data: Array<Kos>) : RecyclerView.Adapter<rvKosAdapter.viewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_kos, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNamaKos.text = currentItem.name
        holder.tvTipeKos.text = "Tipe " + currentItem.tipe
        holder.tvJumlahKos.text = "Jumlah ${currentItem.jumlah}"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNamaKos : TextView = itemView.findViewById(R.id.tv_nama_kos)
        val tvTipeKos : TextView = itemView.findViewById(R.id.tv_tipe_kos)
        val tvJumlahKos : TextView = itemView.findViewById(R.id.tv_jumlah_kos)
    }
}
