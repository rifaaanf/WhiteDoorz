package com.example.whitedoorz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whitedoorz.room.Gedung
import com.example.whitedoorz.room.Ruangan
import kotlinx.android.synthetic.main.adapter_main.view.*

class GedungAdapter (private var gedungs: ArrayList<Gedung>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<GedungAdapter.GedungViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):GedungViewHolder {
        return GedungViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_main,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = gedungs.size

    override fun onBindViewHolder(holder:GedungViewHolder, position: Int) {
        val gedung = gedungs[position]
        holder.view.text_title.text = gedung.gedung
        holder.view.text_lantai.text = gedung.lantai.toString()

        holder.view.container.setOnClickListener {
            listener.onRead(gedung)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(gedung)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(gedung)
        }

    }

    inner class GedungViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Gedung>) {
        gedungs.clear()
        gedungs.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onRead(gedung: Gedung)
        fun onUpdate(gedung: Gedung)
        fun onDelete(gedung: Gedung)
    }
}