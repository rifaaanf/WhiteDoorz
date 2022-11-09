package com.example.whitedoorz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whitedoorz.room.Ruangan
import kotlinx.android.synthetic.main.adapter_main.view.*

class RuanganAdapter (private var ruangans: ArrayList<Ruangan>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<RuanganAdapter.RuanganViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RuanganViewHolder {
        return RuanganViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_main,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = ruangans.size

    override fun onBindViewHolder(holder:RuanganViewHolder, position: Int) {
        val ruangan = ruangans[position]
        holder.view.text_title.text = ruangan.ruangan
        holder.view.text_title.setOnClickListener {
            listener.onRead(ruangan)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(ruangan)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(ruangan)
        }

    }

    inner class RuanganViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Ruangan>) {
        ruangans.clear()
       ruangans.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onRead(ruangan: Ruangan)
        fun onUpdate(ruangan: Ruangan)
        fun onDelete(ruangan: Ruangan)
    }
}