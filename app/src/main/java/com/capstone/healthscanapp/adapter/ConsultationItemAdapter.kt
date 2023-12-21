package com.capstone.healthscanapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.ConsultationItem

class ConsultationItemAdapter(
    private val consultationItems: List<ConsultationItem>,
    private val onItemClick: (ConsultationItem) -> Unit
) : RecyclerView.Adapter<ConsultationItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(consultationItems[position])
                }
            }
        }

        val doctorName: TextView = itemView.findViewById(R.id.itemTitle)
        val specialization: TextView = itemView.findViewById(R.id.itemDescription)
        val tapToChat: TextView = itemView.findViewById(R.id.tapToChatText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_konsultasi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val consultationItem = consultationItems[position]
        holder.doctorName.text = consultationItem.doctorName
        holder.specialization.text = consultationItem.specialization
        holder.tapToChat.text = consultationItem.tapToChat
    }

    override fun getItemCount(): Int {
        return consultationItems.size
    }
}
