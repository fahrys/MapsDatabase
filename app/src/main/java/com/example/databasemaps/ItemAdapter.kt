package com.example.databasemaps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class ItemAdapter (val context: Context , val items: ArrayList<EmpModel>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Linlay = view.linlay
        val tvkegiatan = view.tvkegiatan
        val tvwaktu = view.tvwaktu
        val tvlokasi = view.tvlokasi
        val ivDelete = view.iv_delete
        val no = view.tvid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = items.get(position)
        holder.tvkegiatan.text = item.kegiatan
        holder.tvlokasi.text = item.lokasi
        holder.tvwaktu.text = item.waktu
        holder.no.text = position.toString()
        if(position % 2 == 0) {
            holder.Linlay.setBackgroundColor(ContextCompat.getColor(context, R.color.brown))
        }else{
            holder.Linlay.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
        }
        holder.ivDelete.setOnClickListener{
            if (context is History){
                context.deleteRecordAlertDialog(item)
            }
        }
    }

    override fun getItemCount(): Int {
      return items.size
    }
}